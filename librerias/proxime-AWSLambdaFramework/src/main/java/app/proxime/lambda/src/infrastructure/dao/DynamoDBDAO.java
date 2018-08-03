package app.proxime.lambda.src.infrastructure.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamoDBDAO<DynamoDBModel> {

    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;

    private Class<DynamoDBModel> dynamoDBModel;

    public DynamoDBDAO(Class<DynamoDBModel> dynamoDBModel){
        this.dynamoDBModel = dynamoDBModel;
        this.client = AmazonDynamoDBClientBuilder.standard().build();
        this.mapper = new DynamoDBMapper(client);
    }

    public DynamoDBModel getById(String id) {
        return mapper.load(dynamoDBModel, id);
    }
    public DynamoDBModel getByField(String field, int value) {
        Map<String, AttributeValue> searchParameters = new HashMap<String, AttributeValue>();
        searchParameters.put(":" + field, new AttributeValue().withN(Integer.toString(value)));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(field + "=:" + field)
                .withLimit(1)
                .withExpressionAttributeValues(searchParameters);
        List<DynamoDBModel> scanResult = mapper.parallelScan(dynamoDBModel, scanExpression, 4);
        try {
            return scanResult.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public DynamoDBModel getByField(String field, long value) {
        Map<String, AttributeValue> searchParameters = new HashMap<String, AttributeValue>();
        searchParameters.put(":" + field, new AttributeValue().withN(Long.toString(value)));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(field + "=:" + field)
                .withLimit(1)
                .withExpressionAttributeValues(searchParameters);
        List<DynamoDBModel> scanResult = mapper.parallelScan(dynamoDBModel, scanExpression, 4);
        try {
            return scanResult.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public DynamoDBModel getByField(String field, String value) {
        Map<String, AttributeValue> searchParameters = new HashMap<String, AttributeValue>();
        searchParameters.put(":" + field, new AttributeValue().withS(value));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                //  .withFilterExpression("empresa=:empresa and (begins_with(codigo,:codigo) or begins_with(responsable,:responsable))" )
                .withFilterExpression(field + "=:" + field)
                .withLimit(1)
                .withExpressionAttributeValues(searchParameters);
        List<DynamoDBModel> scanResult = mapper.parallelScan(dynamoDBModel, scanExpression, 4);
        try {
            return scanResult.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public List<DynamoDBModel> getListByField(String field, String value) {
        Map<String, AttributeValue> serachParameters = new HashMap<String, AttributeValue>();
        serachParameters.put(":" + field, new AttributeValue().withS(value));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(field + "=:" + field)
                .withExpressionAttributeValues(serachParameters);
        return mapper.parallelScan(dynamoDBModel, scanExpression, 4);
    }
    public void insertOrUpdate(DynamoDBModel dynamoDBModel) {
        mapper.save(dynamoDBModel);
    }
    public void insertOrUpdate(List<DynamoDBModel> obj) {
        mapper.batchSave(obj);
    }
    public void delete(DynamoDBModel dynamoDBModel) {
        mapper.delete(dynamoDBModel);
    }
    public List<DynamoDBModel> getListBySearchParameters(Map<String, AttributeValue> searchParameters, int limit) {
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
        return mapper.parallelScan(dynamoDBModel, scanExpression, 4);
    }
    public List<DynamoDBModel> getListBySearchParameters(Map<String, AttributeValue> searchParameters) {
        return getListBySearchParameters(searchParameters, 0);
    }
    public List<DynamoDBModel> query(String query, Map<String, AttributeValue> searchParameters, int limit) {
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
        return mapper.parallelScan(dynamoDBModel, scanExpression, 4);
    }
    public List<DynamoDBModel> query(String query, Map<String, AttributeValue> searchParameters) {
        return query(query, searchParameters, 0);
    }
}
