package cl.proxime.lambda.mocks.context;

import cl.proxime.lambda.framework.context.Lambda;

public class SimpleLambda implements Lambda<SimpleRequest, SimpleResponse> {
    @Override
    public SimpleResponse execute(SimpleRequest request) {
        return new SimpleResponse();
    }
}
