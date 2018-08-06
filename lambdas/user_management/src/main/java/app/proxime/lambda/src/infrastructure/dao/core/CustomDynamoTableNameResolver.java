/*
 * Copyright (C) Proxime SpA - Todos los derechos reservados
 * Queda expresamente prohibida la copia o reproducción total o parcial de este archivo
 * sin el permiso expreso y por escrito de Proxime SpA.
 * La detección de un uso no autorizado puede acarrear el inicio de acciones legales.
 */
package app.proxime.lambda.src.infrastructure.dao.core;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class CustomDynamoTableNameResolver extends DynamoDBMapperConfig.DefaultTableNameResolver {
    private final String UNION="_";
    public static String DEFAULT_STATE="develop";
    public static String DEFAULT_COUNTRY ="global";
    @Override
    public String getTableName(Class<?> clazz, DynamoDBMapperConfig config) {
        String stageName=DEFAULT_STATE;
        String countryName= DEFAULT_COUNTRY;
        try {
            //stageName = "";//TODO: get current stage from context singleton
        }catch(Exception e){
        }
        try {
            //countryName =""; //TODO: get current country from context singleton
        }catch(Exception e){
        }
        String tableName = super.getTableName(clazz, config);
        String generatedName=String.join(UNION,countryName,stageName,tableName).toLowerCase();
        return generatedName;
    }
}