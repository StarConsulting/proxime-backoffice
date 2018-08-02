package app.proxime.lambda.mocks.context;

import app.proxime.lambda.framework.annotations.Intercept;
import app.proxime.lambda.framework.context.Lambda;

@Intercept(classPath = "app.proxime.lambda.mocks.filter.SimpleFilter")
public class LambdaWithFilter implements Lambda<SimpleRequest, SimpleResponse> {
    @Override
    public SimpleResponse execute(SimpleRequest request) {
        return new SimpleResponse();
    }
}
