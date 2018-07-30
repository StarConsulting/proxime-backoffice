package cl.proxime.lambda.function.service.Hello;

import cl.proxime.lambda.framework.context.Lambda;
import cl.proxime.lambda.framework.exception.LambdaException;

public class HelloLambda implements Lambda<HelloRequest, HelloResponse> {
    @Override
    public HelloResponse execute(HelloRequest request) throws LambdaException {
        HelloResponse response = new HelloResponse();

        response.greeting = "Saludos "+ request.name;
        return response;
    }
}
