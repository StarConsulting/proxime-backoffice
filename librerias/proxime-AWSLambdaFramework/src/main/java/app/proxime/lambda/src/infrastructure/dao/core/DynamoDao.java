/*
 * Copyright (C) Proxime SpA - Todos los derechos reservados
 * Queda expresamente prohibida la copia o reproducción total o parcial de este archivo
 * sin el permiso expreso y por escrito de Proxime SpA.
 * La detección de un uso no autorizado puede acarrear el inicio de acciones legales.
 */
package app.proxime.lambda.src.infrastructure.dao.core;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class with basic operations of a model on DynamoDB(CRUD)
 * @param <T> Class thar represents the model
 * @author Michel M.
 */
public abstract class DynamoDao<T> {
    static protected AmazonDynamoDB client = (AmazonDynamoDB) AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);
    protected final Class<T> clazz;
    protected DynamoDBMapper mapper = new DynamoDBMapper(client, new DynamoDBMapperConfig(new CustomDynamoTableNameResolver()));
    protected DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
    private final long DEFAULT_WRITE_CAPACITY = 5;
    private final long DEFAULT_READ_CAPACITY = 5;

    public DynamoDao(Class<T> clazz) {
        this.clazz = clazz;
        createTableIfNotExist();
    }

    private void createTableIfNotExist(){
        if(!tableExists()){
            createTable();
        }
    }

    private boolean tableExists(){
        /*try {
        TableDescription tableDescription = dynamoDB.getTable(
                        DynamoDBMapperConfig.
                        DefaultTableNameResolver.
                        INSTANCE.getTableName(clazz,config)
        ).describe();
        return true;
        } catch (ResourceNotFoundException exception) {
                return false;
        }*/
        return true;
    }

    private void createTable() {
        CreateTableRequest request = mapper.generateCreateTableRequest(clazz);
        request.withProvisionedThroughput(
                new ProvisionedThroughput()
                        .withReadCapacityUnits(DEFAULT_READ_CAPACITY)
                        .withWriteCapacityUnits(DEFAULT_WRITE_CAPACITY));
        dynamoDB.createTable(request);
        //TODO: apply autoscaling
    }

    public T getById(String id) {
        return mapper.load(clazz, id, config);
    }
    public T getByField(String field, int value) {
        Map<String, AttributeValue> searchParameters = new HashMap<String, AttributeValue>();
        searchParameters.put(":" + field, new AttributeValue().withN(Integer.toString(value)));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(field + "=:" + field)
                .withLimit(1)
                .withExpressionAttributeValues(searchParameters);
        List<T> scanResult = mapper.parallelScan(clazz, scanExpression, 4);
        try {
            return scanResult.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public T getByField(String field, long value) {
        Map<String, AttributeValue> searchParameters = new HashMap<String, AttributeValue>();
        searchParameters.put(":" + field, new AttributeValue().withN(Long.toString(value)));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(field + "=:" + field)
                .withLimit(1)
                .withExpressionAttributeValues(searchParameters);
        List<T> scanResult = mapper.parallelScan(clazz, scanExpression, 4);
        try {
            return scanResult.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public T getByField(String field, String value) {
        Map<String, AttributeValue> searchParameters = new HashMap<String, AttributeValue>();
        searchParameters.put(":" + field, new AttributeValue().withS(value));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                //  .withFilterExpression("empresa=:empresa and (begins_with(codigo,:codigo) or begins_with(responsable,:responsable))" )
                .withFilterExpression(field + "=:" + field)
                .withLimit(1)
                .withExpressionAttributeValues(searchParameters);
        List<T> scanResult = mapper.parallelScan(clazz, scanExpression, 4);
        try {
            return scanResult.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public List<T> getListByField(String field, String value) {
        Map<String, AttributeValue> serachParameters = new HashMap<String, AttributeValue>();
        serachParameters.put(":" + field, new AttributeValue().withS(value));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(field + "=:" + field)
                .withExpressionAttributeValues(serachParameters);
        return mapper.parallelScan(clazz, scanExpression, 4);
    }
    public void insertOrUpdate(T obj) {
        mapper.save(obj);
    }
    public void insertOrUpdate(List<T> obj) {
        mapper.batchSave(obj);
    }
    public void delete(T obj) {
        mapper.delete(obj);
    }
    public List<T> getListBySearchParameters(Map<String, AttributeValue> searchParameters, int limit) {
        List<String> conditions = new ArrayList<>();
        if (searchParameters != null) {
            for (Map.Entry<String, AttributeValue> p : searchParameters.entrySet()) {
                searchParameters.put(":" + p.getKey(), p.getValue());
                conditions.add(p.getKey() + "=:" + p.getKey());
            }
        }
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(String.join(" and ", conditions))
                .withExpressionAttributeValues(searchParameters);
        if (limit > 0) {
            scanExpression.withLimit(1);
        }
        return mapper.parallelScan(clazz, scanExpression, 4);
    }
    public List<T> getListBySearchParameters(Map<String, AttributeValue> searchParameters) {
        return getListBySearchParameters(searchParameters, 0);
    }
    public List<T> query(String query, Map<String, AttributeValue> searchParameters, int limit) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        if (query != null && !query.isEmpty()) {
            scanExpression.withFilterExpression(query);
        }
        if (searchParameters != null && searchParameters.size() > 0) {
            scanExpression.withExpressionAttributeValues(searchParameters);
        }
        if (limit > 0) {
            scanExpression.withLimit(1);
        }
        return mapper.parallelScan(clazz, scanExpression, 4);
    }
    public List<T> query(String query, Map<String, AttributeValue> searchParameters) {
        return query(query, searchParameters, 0);
    }
}
