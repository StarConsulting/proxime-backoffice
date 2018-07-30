/*
 * Copyright (C) Proxime SpA - Todos los derechos reservados
 * Queda expresamente prohibida la copia o reproducción total o parcial de este archivo
 * sin el permiso expreso y por escrito de Proxime SpA.
 * La detección de un uso no autorizado puede acarrear el inicio de acciones legales.
 */
package appp.proxime.maven.plugins;

import appp.proxime.maven.plugins.config.structure.ApiGateway;
import appp.proxime.maven.plugins.config.structure.Lambda;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.AmazonApiGatewayClientBuilder;
import com.amazonaws.services.apigateway.model.ContentHandlingStrategy;
import com.amazonaws.services.apigateway.model.CreateDocumentationPartRequest;
import com.amazonaws.services.apigateway.model.CreateResourceRequest;
import com.amazonaws.services.apigateway.model.CreateResourceResult;
import com.amazonaws.services.apigateway.model.DeleteMethodRequest;
import com.amazonaws.services.apigateway.model.DeleteMethodResponseRequest;
import com.amazonaws.services.apigateway.model.DocumentationPartLocation;
import com.amazonaws.services.apigateway.model.DocumentationPartType;
import com.amazonaws.services.apigateway.model.GetResourcesRequest;
import com.amazonaws.services.apigateway.model.GetResourcesResult;
import com.amazonaws.services.apigateway.model.IntegrationType;
import com.amazonaws.services.apigateway.model.Method;
import com.amazonaws.services.apigateway.model.PutIntegrationRequest;
import com.amazonaws.services.apigateway.model.PutIntegrationResponseRequest;
import com.amazonaws.services.apigateway.model.PutIntegrationResult;
import com.amazonaws.services.apigateway.model.PutMethodRequest;
import com.amazonaws.services.apigateway.model.PutMethodResponseRequest;
import com.amazonaws.services.apigateway.model.PutMethodResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.AddPermissionRequest;
import com.amazonaws.services.lambda.model.AliasConfiguration;
import com.amazonaws.services.lambda.model.CreateAliasRequest;
import com.amazonaws.services.lambda.model.CreateAliasResult;
import com.amazonaws.services.lambda.model.CreateFunctionRequest;
import com.amazonaws.services.lambda.model.CreateFunctionResult;
import com.amazonaws.services.lambda.model.DeleteFunctionRequest;
import com.amazonaws.services.lambda.model.Environment;
import com.amazonaws.services.lambda.model.FunctionCode;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.GetFunctionRequest;
import com.amazonaws.services.lambda.model.GetFunctionResult;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ListAliasesRequest;
import com.amazonaws.services.lambda.model.ListAliasesResult;
import com.amazonaws.services.lambda.model.ListVersionsByFunctionRequest;
import com.amazonaws.services.lambda.model.ListVersionsByFunctionResult;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeRequest;
import com.amazonaws.services.lambda.model.UpdateFunctionConfigurationRequest;
import com.amazonaws.services.lambda.model.VpcConfig;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Maven Plugin para facilitar la configuración de API Gateway y Lambda. Permite
 * configurar el lmabda y api dateway desde el POM del proyecto maven.
 *
 * @author Michel M. <michel@febos.cl>
 */
@Mojo(name = "configure")
public class ProximeMojoConfigure extends AbstractMojo {

    @Parameter
    boolean update;
    @Parameter
    boolean deleteJars;
    @Parameter(defaultValue = "")
    String credencialesAWS;
    @Parameter
    List<ApiGateway> apiGateway;
    @Parameter
    Lambda lambda;
    CustomCredentialsProvider credenciales;
    AmazonS3 s3client;
    AWSLambda lambdaClient;
    AmazonApiGateway apiClient;
    HashMap<String, Boolean> recursos;
    HashMap<String, String> recursosId;
    HashMap<String, ArrayList<Method>> recursoMetodos;
    boolean lambdaNuevo;

    public static void main(String[] args) {
        AmazonApiGateway client = AmazonApiGatewayClientBuilder.defaultClient();
        
        CreateDocumentationPartRequest doc=new CreateDocumentationPartRequest();
        
        DocumentationPartLocation location=new DocumentationPartLocation();
        
        location.setType(DocumentationPartType.MODEL);
        location.setName("UsuarioResponseModel");
        doc.setLocation(location);
        
        doc.setProperties("");
                
                
        client.createDocumentationPart(doc);
        
    }

    public void configurarApiGateway(String apiID, String resourceID, String verbo, String lambdaName, Map<String, String> template) {
        if (apiID == null || apiID.isEmpty()) {
            return;
        }
        apiClient = AmazonApiGatewayClientBuilder.defaultClient();
        lambdaClient = AWSLambdaClientBuilder.defaultClient();

        Map<String, String> emptyModels = new HashMap<>();
        emptyModels.put("application/json", "Empty");
        emptyModels.put("application/xml", "Empty");

        //Map<String, String> emptyXmlModel = new HashMap<>();
        //emptyXmlModel.put("application/xml", "Empty");
        try {
            System.out.print("-> Eliminando configuración actual...");
            DeleteMethodRequest dmr = new DeleteMethodRequest();
            dmr.setHttpMethod(verbo);
            dmr.setResourceId(resourceID);
            dmr.setRestApiId(apiID);
            apiClient.deleteMethod(dmr);
            System.out.print("[OK]\n");
        } catch (Exception e) {
            System.out.print("[No habia configuración previa]\n");
        }

        try {
            System.out.print("-> Eliminando configuración CORS actual...");
            DeleteMethodRequest dmr = new DeleteMethodRequest();
            dmr.setHttpMethod("OPTIONS");
            dmr.setResourceId(resourceID);
            dmr.setRestApiId(apiID);
            apiClient.deleteMethod(dmr);
            System.out.print("[OK]\n");
        } catch (Exception e) {
            System.out.print("[No habia configuración previa]\n");
        }

        try {
            System.out.print("-> Creando Metodo " + verbo + "... ");
            PutMethodRequest pmr = new PutMethodRequest();
            pmr.setHttpMethod(verbo);
            pmr.setOperationName(lambdaName);
            pmr.setResourceId(resourceID);
            pmr.setRestApiId(apiID);
            pmr.setAuthorizationType("NONE");

            Map<String, Boolean> parametrosR = new HashMap<>();
            parametrosR.put("method.request.header.token", false);
            parametrosR.put("method.request.header.ambito", false);
            parametrosR.put("method.request.header.empresa", false);
            parametrosR.put("method.request.header.describe", false);
            parametrosR.put("method.request.header.rutCliente", false);
            parametrosR.put("method.request.header.rutProveedor", false);
            pmr.setRequestParameters(parametrosR);

            //pmr.setRequestModels(emptyModel);
            PutMethodResult putMethod = apiClient.putMethod(pmr);
            System.out.print("[OK]\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("[Ya existia el metodo]\n");
        }

        try {
            System.out.print("-> Creando Metodo OPTIONS... ");
            PutMethodRequest pmr = new PutMethodRequest();
            pmr.setHttpMethod("OPTIONS");
            pmr.setOperationName("cors-"+lambdaName);
            pmr.setResourceId(resourceID);
            pmr.setRestApiId(apiID);
            pmr.setAuthorizationType("NONE");

            Map<String, Boolean> parametrosR = new HashMap<>();
            parametrosR.put("method.request.header.token", false);
            parametrosR.put("method.request.header.ambito", false);
            parametrosR.put("method.request.header.empresa", false);
            parametrosR.put("method.request.header.describe", false);
            parametrosR.put("method.request.header.rutCliente", false);
            parametrosR.put("method.request.header.rutProveedor", false);
            pmr.setRequestParameters(parametrosR);

            //pmr.setRequestModels(emptyModel);
            PutMethodResult putMethod = apiClient.putMethod(pmr);
            System.out.print("[OK]\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("[Ya existia el metodo]\n");
        }

        Map<String, String> velocity = new HashMap<>();
        String tmplJson = "{\n";
        String tmplXml = "{\n";
        int item = 1;
        for (Map.Entry<String, String> parametro : template.entrySet()) {
            String envoltorio = parametro.getValue().contains(".json(") ? "" : "\"";

            tmplJson += "  \"" + parametro.getKey() + "\":" + envoltorio + parametro.getValue();
            if (!envoltorio.isEmpty()) {
                tmplXml += "  \"" + parametro.getKey() + "\":" + envoltorio + parametro.getValue();
            }

            if (item == template.size()) {

                if (!envoltorio.isEmpty()) {
                    tmplXml += envoltorio + "\n}";
                } else {
                    tmplXml += "\"xmlBodyComoEntrada\":\"$util.base64Encode($input.json('$'))\"\n}";
                }
                tmplJson += envoltorio + "\n}";//fin del json
            } else {
                tmplJson += envoltorio + ",\n";
                if (!envoltorio.isEmpty()) {
                    tmplXml += envoltorio + ",\n";
                }
            }
            item++;
        }

        velocity.put("application/json", tmplJson);
        velocity.put("application/xml", tmplXml);

        System.out.print("-> Configurando API para interactuar con el lambda... ");
        PutIntegrationRequest pir = new PutIntegrationRequest();
        pir.setIntegrationHttpMethod("POST");
        pir.setHttpMethod(verbo);
        pir.setPassthroughBehavior("WHEN_NO_TEMPLATES");
        pir.setType(IntegrationType.AWS);
        pir.setResourceId(resourceID);
        pir.setCredentials("");
        pir.setRestApiId(apiID);
        pir.setUri("arn:aws:apigateway:us-east-1:lambda:path/2015-03-31/functions/arn:aws:lambda:us-east-1:830321976775:function:" + lambdaName + ":${stageVariables.ambiente}/invocations");
        pir.setRequestTemplates(velocity);
        pir.setContentHandling(ContentHandlingStrategy.CONVERT_TO_TEXT);
        PutIntegrationResult putIntegration = apiClient.putIntegration(pir);
        System.out.print("[OK]\n");

        System.out.print("-> Configurando API para interactuar con el FRONT END... ");
        pir = new PutIntegrationRequest();
        //pir.setIntegrationHttpMethod("POST");
        pir.setHttpMethod("OPTIONS");
        pir.setType(IntegrationType.MOCK);
        pir.setResourceId(resourceID);
        pir.setPassthroughBehavior("WHEN_NO_TEMPLATES");
        pir.setRestApiId(apiID);
        Map<String, String> http200 = new HashMap<>();
        http200.put("application/json", "{\"statusCode\": 200}");
        http200.put("application/xml", "{\"statusCode\": 200}");
        pir.setRequestTemplates(http200);
        putIntegration = apiClient.putIntegration(pir);
        System.out.print("[OK]\n");

        try {
            System.out.print("-> Eliminando actual configuración de Response Method para API... ");
            DeleteMethodResponseRequest dmrr = new DeleteMethodResponseRequest();
            dmrr.setHttpMethod(verbo);
            dmrr.setStatusCode("200");
            dmrr.setResourceId(resourceID);
            dmrr.setRestApiId(apiID);
            apiClient.deleteMethodResponse(dmrr);
            System.out.print("[OK]\n");
        } catch (Exception e) {
            System.out.print("[No existia el metodo http code 200]\n");
        }

        try {
            System.out.print("-> Eliminando actual OPTIONS Response Method para API (CORS)... ");
            DeleteMethodResponseRequest dmrr = new DeleteMethodResponseRequest();
            dmrr.setHttpMethod("OPTIONS");
            dmrr.setStatusCode("200");
            dmrr.setResourceId(resourceID);
            dmrr.setRestApiId(apiID);
            apiClient.deleteMethodResponse(dmrr);
            System.out.print("[OK]\n");
        } catch (Exception e) {
            System.out.print("[No existia el metodo http code 200]\n");
        }

        try {
            System.out.print("-> Creando Reponse con cabeceras de CORS... ");
            PutMethodResponseRequest pmrr = new PutMethodResponseRequest();
            pmrr.setHttpMethod(verbo);
            pmrr.setResourceId(resourceID);
            pmrr.setRestApiId(apiID);
            pmrr.setStatusCode("200");
            pmrr.setResponseModels(emptyModels);
            Map<String, Boolean> h = new HashMap<>();
            h.put("method.response.header.Access-Control-Allow-Headers", true);
            h.put("method.response.header.Access-Control-Allow-Origin", true);
            h.put("method.response.header.Access-Control-Allow-Methods", true);
            pmrr.setResponseParameters(h);
            apiClient.putMethodResponse(pmrr);
            System.out.print("[OK]\n");
        } catch (Exception e) {
            System.out.print("[Ya existia el http code 200]\n");
        }

        try {
            System.out.print("-> Creando Reponse con cabeceras de CORS para metodo OPTIONS... ");
            PutMethodResponseRequest pmrr = new PutMethodResponseRequest();
            pmrr.setHttpMethod("OPTIONS");
            pmrr.setResourceId(resourceID);
            pmrr.setRestApiId(apiID);
            pmrr.setStatusCode("200");
            pmrr.setResponseModels(emptyModels);

            Map<String, Boolean> h = new HashMap<>();
            h.put("method.response.header.Access-Control-Allow-Headers", true);
            h.put("method.response.header.Access-Control-Allow-Origin", true);
            h.put("method.response.header.Access-Control-Allow-Methods", true);
            pmrr.setResponseParameters(h);
            apiClient.putMethodResponse(pmrr);
            System.out.print("[OK]\n");
        } catch (Exception e) {
            System.out.print("[Ya existia el http code 200]\n");
        }

        System.out.print("-> Creando integracion con mapping para CORS... ");
        PutIntegrationResponseRequest pirr = new PutIntegrationResponseRequest();
        pirr.setHttpMethod(verbo);
        pirr.setResourceId(resourceID);
        pirr.setRestApiId(apiID);
        pirr.setStatusCode("200");
        Map<String, String> l = new HashMap<>();
        l.put("application/json", "");
        l.put("application/xml", "");
        pirr.setResponseTemplates(l);
        Map<String, String> params = new HashMap<>();
        params.put("method.response.header.Access-Control-Allow-Headers", "'Accept,Content-Type,X-Amz-Date,Authorization,X-Api-Key,token,empresa,grupo,describe,ambito,rutCliente,rutProveedor,debug'");
        params.put("method.response.header.Access-Control-Allow-Origin", "'*'");
        params.put("method.response.header.Access-Control-Allow-Methods", "'GET,PUT,OPTIONS,POST,DELETE,HEAD'");
        pirr.setResponseParameters(params);
        apiClient.putIntegrationResponse(pirr);
        System.out.print("[OK]\n");

        System.out.print("-> Creando integracion con mapping para CORS (OPTIONS)... ");
        pirr = new PutIntegrationResponseRequest();
        pirr.setHttpMethod("OPTIONS");
        pirr.setResourceId(resourceID);
        pirr.setRestApiId(apiID);
        pirr.setStatusCode("200");
        l = new HashMap<>();
        l.put("application/json", "");
        l.put("application/xml", "");
        pirr.setResponseTemplates(l);
        params = new HashMap<>();
        params.put("method.response.header.Access-Control-Allow-Headers", "'Accept,Content-Type,X-Amz-Date,Authorization,X-Api-Key,token,empresa,grupo,describe,ambito,rutCliente,rutProveedor,debug'");
        params.put("method.response.header.Access-Control-Allow-Origin", "'*'");
        params.put("method.response.header.Access-Control-Allow-Methods", "'GET,PUT,OPTIONS,POST,DELETE,HEAD'");
        pirr.setResponseParameters(params);
        apiClient.putIntegrationResponse(pirr);
        System.out.print("[OK]\n");
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!update) {
            getLog().info("Upload desactivado");
            return;
        }
        lambdaNuevo = false;

        try {
            lambdaClient = AWSLambdaClientBuilder.defaultClient();
            s3client = AmazonS3ClientBuilder.defaultClient();
            apiClient = AmazonApiGatewayClientBuilder.defaultClient();


            getLog().info("Subiendo package a S3 (" + (new File(lambda.localFile()).length() / 1000000) + " MB)");
            getLog().info(lambda.localFile());
            String bucket = lambda.s3File().split("/")[0];
            String s3path = lambda.s3File().substring(lambda.s3File().indexOf("/") + 1);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3path, new File(lambda.localFile()));
            final long fileSize = new File(lambda.localFile()).length();
            System.out.println("");
            putObjectRequest.setGeneralProgressListener(new ProgressListener() {
                long bytesSubidos = 0;
                HashMap<Integer, Boolean> avance = new HashMap<>();

                @Override
                public void progressChanged(ProgressEvent pe) {
                    bytesSubidos += pe.getBytesTransferred();
                    int porcentaje = (int) (bytesSubidos * 100 / fileSize);
                    if (!avance.getOrDefault(porcentaje, false)) {
                        avance.put(porcentaje, true);
                        if (porcentaje % 2 == 0) {
                            String bar = "[";
                            for (int i = 2; i <= 100; i = i + 2) {
                                if (i <= porcentaje && porcentaje != 0) {
                                    bar += "#";
                                } else {
                                    bar += " ";
                                }
                            }
                            bar += "] " + porcentaje + "%";
                            System.out.print("\r" + bar);
                            if (porcentaje == 100) {
                                System.out.println("\nListo!");
                            }
                        }
                    }

                }
            });
            s3client.putObject(putObjectRequest);
            getLog().info("--> [OK]");

            //leyendo properties para establecer permisos
            String ruta = new File(lambda.localFile()).getParentFile().getAbsolutePath();
            ruta += ruta.endsWith("/") ? "" : "/";
            ruta += "classes/maven.properties";
            Properties prop = new Properties();
            InputStream input = null;
            try {
                input = new FileInputStream(ruta);
                // load a properties file
                prop.load(input);

            } catch (IOException ex) {
                //ex.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }
            //

            if (!functionExists(lambda.nombre())) {
                lambdaNuevo = true;
                getLog().info("Creando funcion  lambda");
                try {
                    CreateFunctionRequest nuevoLambda = new CreateFunctionRequest();
                    VpcConfig vpcConfig = null;
                    if (lambda.vpc() != null) {
                        vpcConfig = new VpcConfig();

                        String[] securityGroups = lambda.vpc().split("SecurityGroupIds")[1].replace("=", "").split(",");
                        String[] subnets = lambda.vpc().split(",SecurityGroupIds")[0].replace("SubnetIds=", "").split(",");
                        //vpcConfig.withSecurityGroupIds(lambda.vpc().split(",")[1].split("=")[1]);
                        //vpcConfig.withSubnetIds(lambda.vpc().split(",")[0].split("=")[1]);
                        vpcConfig.withSecurityGroupIds(securityGroups);
                        vpcConfig.withSubnetIds(subnets);
                    }

                    nuevoLambda
                            .withFunctionName(lambda.nombre())
                            .withDescription(lambda.descripcion())
                            .withPublish(true)
                            .withHandler(lambda.handler())
                            .withMemorySize(lambda.ram())
                            .withTimeout(lambda.timeout())
                            .withRuntime("java8")
                            .withCode(new FunctionCode().withS3Bucket(bucket).withS3Key(s3path));
                    try {
                        getLog().info("Seteando variables de entorno para el lambda");
                        nuevoLambda.setEnvironment(new Environment());
                        for (Map.Entry<Object, Object> set : prop.entrySet()) {
                            if (((String) set.getKey()).startsWith("febos")) {
                                String key = (String) set.getKey();
                                key = key.replaceAll("\\.", "_");
                                String value = (String) set.getValue();
                                getLog().info("  -> " + key + " = " + value);
                                nuevoLambda.getEnvironment().addVariablesEntry(key, value);
                            }
                        }
                        getLog().info("--> [OK]");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (lambda.role() != null && !lambda.role().isEmpty()) {
                        nuevoLambda.withRole(lambda.role());
                    }
                    if (vpcConfig != null) {
                        nuevoLambda.withVpcConfig(vpcConfig);
                    }
                    CreateFunctionResult createFunction = lambdaClient.createFunction(nuevoLambda);
                    System.out.println(new Gson().toJson(createFunction));
                    getLog().info("--> [OK]");
                    String[] ambientes = new String[]{"desarrollo", "pruebas", "certificacion", "produccion"};
                    getLog().info("Creando alias para los distintos ambientes");
                    for (String ambiente : ambientes) {
                        getLog().info("--> Configurando " + ambiente);
                        CreateAliasResult createAlias = lambdaClient.createAlias(new CreateAliasRequest()
                                .withFunctionName(lambda.nombre())
                                .withName(ambiente)
                                .withFunctionVersion("$LATEST")
                        );
                        System.out.println(createAlias.getName()+" : "+createAlias.getDescription());
                        lambdaClient.addPermission(new AddPermissionRequest()
                                .withFunctionName("arn:aws:lambda:"
                                        + "us-east-1" //credenciales.props.getProperty("region")
                                        + ":" + "830321976775" //credenciales.props.getProperty("id")
                                        + ":function:" + lambda.nombre() + ":" + ambiente)
                                .withSourceArn("arn:aws:execute-api:" + "us-east-1" + ":" + "830321976775" + ":*")
                                .withPrincipal("apigateway.amazonaws.com")
                                .withStatementId(UUID.randomUUID().toString())
                                .withAction("lambda:InvokeFunction")
                        );
                    }
                    getLog().info("--> [OK]");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                getLog().info("Actualizando codigo lambda");
                UpdateFunctionCodeRequest updateLambda = new UpdateFunctionCodeRequest();
                updateLambda.withFunctionName(lambda.nombre())
                        .withPublish(true)
                        .withS3Bucket(bucket)
                        .withS3Key(s3path);

                lambdaClient.updateFunctionCode(updateLambda);
                getLog().info("--> [OK]");
                getLog().info("Actualizando configuracion");
                UpdateFunctionConfigurationRequest configureLambda = new UpdateFunctionConfigurationRequest();
                VpcConfig vpcConfig = null;

                if (lambda.vpc() != null) {
                    vpcConfig = new VpcConfig();
                    String[] securityGroups = lambda.vpc().split("SecurityGroupIds")[1].replace("=", "").split(",");
                    String[] subnets = lambda.vpc().split(",SecurityGroupIds")[0].replace("SubnetIds=", "").split(",");
                    //vpcConfig.withSecurityGroupIds(lambda.vpc().split(",")[1].split("=")[1]);
                    //vpcConfig.withSubnetIds(lambda.vpc().split(",")[0].split("=")[1]);
                    vpcConfig.withSecurityGroupIds(securityGroups);
                    vpcConfig.withSubnetIds(subnets);

                }

                configureLambda
                        .withFunctionName(lambda.nombre())
                        .withDescription(lambda.descripcion())
                        .withHandler(lambda.handler())
                        .withMemorySize(lambda.ram())
                        .withTimeout(lambda.timeout())
                        .withRuntime("java8");

                try {
                    getLog().info("Seteando variables de entorno para el lambda");
                    configureLambda.setEnvironment(new Environment());

                    for (Map.Entry<Object, Object> set : prop.entrySet()) {
                        if (((String) set.getKey()).startsWith("febos")) {
                            String key = (String) set.getKey();
                            key = key.replaceAll("\\.", "_");
                            String value = (String) set.getValue();
                            getLog().info("  -> " + key + " = " + value);
                            configureLambda.getEnvironment().addVariablesEntry(key, value);
                        }
                    }
                    getLog().info("--> [OK]");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (lambda.role() != null && !lambda.role().isEmpty()) {
                    configureLambda.withRole(lambda.role());
                }
                if (vpcConfig != null) {
                    configureLambda.withVpcConfig(vpcConfig);
                }
                lambdaClient.updateFunctionConfiguration(configureLambda);
                getLog().info("--> [OK]");
            }

            HashMap<Integer, String> versiones = new HashMap<>();
            getLog().info("Eliminando versiones sin uso");
            ListVersionsByFunctionRequest reqListVersiones = new ListVersionsByFunctionRequest();
            reqListVersiones.setFunctionName(lambda.nombre());
            ListVersionsByFunctionResult listVersionsByFunction = lambdaClient.listVersionsByFunction(reqListVersiones);
            int maxVersion = 0;
            for (FunctionConfiguration conf : listVersionsByFunction.getVersions()) {
                if (!conf.getVersion().equals("$LATEST")) {
                    int ver = Integer.parseInt(conf.getVersion());
                    versiones.put(ver, null);
                    if (ver > maxVersion) {
                        maxVersion = ver;
                    }
                }
            }
            ListAliasesRequest reqListAlias = new ListAliasesRequest();
            reqListAlias.setFunctionName(lambda.nombre());
            ListAliasesResult listAliases = lambdaClient.listAliases(reqListAlias);
            for (AliasConfiguration alias : listAliases.getAliases()) {
                if (!alias.getFunctionVersion().equals("$LATEST")) {
                    versiones.put(Integer.parseInt(alias.getFunctionVersion().trim()), alias.getName());
                }
            }
            final int maxVer = maxVersion;
            versiones.entrySet().stream().forEach((v) -> {
                if (v.getValue() == null && v.getKey() != maxVer) {
                    try {
                        DeleteFunctionRequest delReq = new DeleteFunctionRequest();
                        delReq.setFunctionName(lambda.nombre());
                        delReq.setQualifier(v.getKey().toString());
                        getLog().info("Eliminando version " + v.getKey() + " (no tiene alias)");
                        lambdaClient.deleteFunction(delReq);

                    } catch (Exception e) {

                    }
                } else {
                    String alias = v.getValue() == null ? "ultima version" : "alias " + v.getValue();
                    getLog().info("Conservando version " + v.getKey() + " ( " + alias + " )");
                }
            });

            if (deleteJars) {
                File[] archivos = new File(lambda.localFile()).getParentFile().listFiles();
                for (File archivo : archivos) {
                    if (archivo.isFile()) {
                        archivo.delete();
                    }
                }
            }
            getLog().info("Precalentando Lambda...");
            AWSLambda cliente = AWSLambdaClientBuilder.defaultClient();
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName(lambda.nombre());
            invokeRequest.setQualifier("desarrollo");
            invokeRequest.setPayload("{\"stage\":\"desarrollo\",\"warmup\":\"si\"}");
            InvokeResult invoke = cliente.invoke(invokeRequest);
            getLog().info(new String(invoke.getPayload().array()));

            if (apiGateway != null) {
                if (lambdaNuevo) {
                    getLog().info("Configurando API Gateway para el nuevo lambda");
                } else {
                    getLog().info("Re-configurando API Gateway para el lambda");
                }

                for(ApiGateway api:apiGateway){
                    configurarApiGateway(api.api(), api.resource(), api.metodo(), lambda.nombre(), api.mapping());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean functionExists(String name) {
        try {
            GetFunctionRequest gfr = new GetFunctionRequest();
            gfr.setFunctionName(name);
            GetFunctionResult function = lambdaClient.getFunction(gfr);
            if (function != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;

    }

    public void cearEndpoint(String metodo, String url, String lambda, String mapping,ApiGateway apiGateway) {
        String partId = null;
        if (!recursoExiste(url,apiGateway)) {
            partId = crearRecurso(url,apiGateway);
        } else {
            partId = recursoId(url,apiGateway);
        }

        boolean metodoExiste = false;
        Method metodoActual = null;
        for (Method m : recursoMetodos.get(url)) {
            if (m.getHttpMethod().toUpperCase().contains(metodo.toUpperCase())) {
                metodoActual = m;
                metodoExiste = true;
            }
        }
        if (!metodoExiste) {
            PutMethodResult putMethod = apiClient.putMethod(new PutMethodRequest()
                    .withRestApiId(apiGateway.api())
                    .withHttpMethod(metodo)
                    .withResourceId(partId)
            );
            apiClient.putIntegration(new PutIntegrationRequest()
                    .withType(IntegrationType.AWS)
                    .withUri(lambda + ":")
            );
        }

    }

    public Method crearMetodo(String partId, String Metodo) {
        return null;
    }

    public String crearRecurso(String url,ApiGateway apiGateway) {
        url = url.startsWith("/") ? url.substring(1) : url;
        url = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
        String lastId = "";
        String[] parts = url.split("/");
        CreateResourceResult createResource = null;
        for (int i = 0; i < parts.length; i++) {
            String acumulado = "/";
            for (int j = 0; j < i; j++) {
                acumulado += (j != 0) ? "/" + parts[j] : parts[j];
            }

            if (!recursoExiste(acumulado + "/" + parts[i],apiGateway)) {
                createResource = apiClient.createResource(new CreateResourceRequest()
                        .withRestApiId(apiGateway.api())
                        .withPathPart(parts[i])
                        .withParentId(recursoId(acumulado,apiGateway))
                );
            }
        }
        return createResource.getId();
    }

    public void cargarRecursos(ApiGateway apiGateway) {
        if (recursos == null) {
            recursos = new HashMap<>();
            GetResourcesResult resources = apiClient.getResources(new GetResourcesRequest()
                    .withRestApiId(apiGateway.api())
            );
            resources.getItems().stream().forEach((r) -> {
                recursos.put(r.getPath(), true);
                recursosId.put(r.getPath(), r.getId());
                ArrayList<Method> metodos = new ArrayList<>();
                if (r.getResourceMethods() != null) {
                    r.getResourceMethods().entrySet().stream().forEach((m) -> {
                        metodos.add(m.getValue());
                    });
                }
                recursoMetodos.put(r.getPath(), metodos);
            });
        }
    }

    public String recursoId(String url,ApiGateway apiGateway) {
        cargarRecursos(apiGateway);
        return recursosId.getOrDefault(url, null);
    }

    public boolean recursoExiste(String url,ApiGateway apiGateway) {
        cargarRecursos(apiGateway);
        return recursos.getOrDefault(url, Boolean.FALSE);
    }

}
