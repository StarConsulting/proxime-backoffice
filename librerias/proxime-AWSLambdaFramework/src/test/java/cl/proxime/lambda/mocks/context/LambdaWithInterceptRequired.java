package cl.proxime.lambda.mocks.context;

import cl.proxime.lambda.framework.annotations.Intercept;
import cl.proxime.lambda.framework.context.Lambda;

@Intercept(classPath = "cl.proxime.lambda.mocks.filter.SimpleFilter")
public class LambdaWithInterceptRequired implements Lambda<SimpleRequest, SimpleResponse> {

    @Override
    public SimpleResponse execute(SimpleRequest request) {
        return new SimpleResponse();
    }
}
