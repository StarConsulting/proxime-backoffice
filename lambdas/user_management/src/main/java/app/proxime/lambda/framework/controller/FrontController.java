package app.proxime.lambda.framework.controller;

import app.proxime.lambda.framework.exception.InternalErrorMessages;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.framework.service.Service;
import app.proxime.lambda.http.Services;

public class FrontController {

    private Services services;

    public FrontController() {
        this.services = new Services();
    }

    public Service findService(Input input) throws LambdaException{

        String functionName = (String) input.jsonToMap().get("functionName");

        Service service = services.get(functionName);

        if (service == null){
            LambdaException exception = new LambdaException(
                    InternalErrorMessages.SERVICE_NOT_FOUND.getId(),
                    InternalErrorMessages.SERVICE_NOT_FOUND.getMessage()
            );
            exception.addError(functionName + " does not exist");
            throw exception;
        }

        return services.get(functionName);
    }
}
