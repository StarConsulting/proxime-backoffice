package app.proxime.lambda.mocks.context;

import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.exception.LambdaExceptionHandler;

public class LambdaWithException implements Lambda<SimpleRequest, SimpleResponse> {
    @Override
    public SimpleResponse execute(SimpleRequest request) throws LambdaException {
        LambdaExceptionHandler lambdaExceptionHandler = new LambdaExceptionHandler();
        lambdaExceptionHandler.setMessage("Test message");
        throw lambdaExceptionHandler.throwException();
    }
}
