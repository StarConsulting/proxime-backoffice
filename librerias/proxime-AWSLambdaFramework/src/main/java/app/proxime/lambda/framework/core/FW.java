package app.proxime.lambda.framework.core;

import app.proxime.lambda.framework.controller.FrontController;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.exception.LambdaExceptionHandler;
import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.framework.service.Service;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FW {

    private FrontController frontController;
    private LambdaExceptionHandler lambdaExceptionHandler;

    public FW(){
        this.frontController = new FrontController();
        this.lambdaExceptionHandler = new LambdaExceptionHandler();
    }

    public void run(
            InputStream inputStream,
            OutputStream outputStream,
            Context context
    ) throws IOException {

        Input input = Input.fromInputStream(inputStream);

        Service service = frontController.findService(input);

        if (service == null){
            String functionName = (String) input.jsonToMap().get("functionName");
            outputStream.write(castNotFoundServiceException(functionName).getMessage().getBytes());;
        }

        service.execute(input, outputStream, context);
    }

    public LambdaException castNotFoundServiceException(String functionName){
        lambdaExceptionHandler.setId("0");
        lambdaExceptionHandler.setErrorType("Service invoke");
        lambdaExceptionHandler.setMessage("Error at invoke "+functionName+" service");
        return lambdaExceptionHandler.throwException();
    }
}
