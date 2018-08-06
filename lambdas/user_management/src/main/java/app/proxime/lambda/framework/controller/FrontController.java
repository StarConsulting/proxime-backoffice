package app.proxime.lambda.framework.controller;

import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.framework.service.Service;
import app.proxime.lambda.http.Services;

public class FrontController {

    private Services services;

    public FrontController() {
        this.services = new Services();
    }

    public Service findService(Input input){

        String functionName = (String) input.jsonToMap().get("functionName");

        return services.get(functionName);
    }
}
