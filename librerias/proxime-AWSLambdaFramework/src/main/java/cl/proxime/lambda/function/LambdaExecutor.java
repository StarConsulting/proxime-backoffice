package cl.proxime.lambda.function;

import cl.proxime.lambda.framework.context.LambdaContext;
import cl.proxime.lambda.framework.input.Input;
import cl.proxime.lambda.framework.interceptor.Interceptor;
import cl.proxime.lambda.function.service.Hello.HelloLambda;
import cl.proxime.lambda.function.service.Hello.HelloRequest;
import cl.proxime.lambda.function.service.Hello.HelloResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LambdaExecutor implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        Interceptor interceptor = new Interceptor();
        LambdaContext lambdaContext = interceptor.buildLambdaContext(
                new HelloLambda(),
                new HelloRequest(),
                new HelloResponse()
        );
        String response = interceptor.executeContext(
                lambdaContext,
                Input.fromInputStream(input).inputStreamToString()
        );
        output.write(response.getBytes());
    }
}
