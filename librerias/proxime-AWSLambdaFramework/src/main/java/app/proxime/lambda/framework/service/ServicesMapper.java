package app.proxime.lambda.framework.service;

import java.util.HashMap;
import java.util.Map;

public class ServicesMapper {

    protected Map<String, Service> mappedServices = new HashMap();

    protected void declare(String functionName, Service service){
        this.mappedServices.put(functionName, service);
    }

    public Service get(String functionName){
        return mappedServices.get(functionName);
    }
}
