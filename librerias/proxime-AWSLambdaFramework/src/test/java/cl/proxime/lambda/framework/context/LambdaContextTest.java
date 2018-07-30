package cl.proxime.lambda.framework.context;

import cl.proxime.lambda.mocks.context.SimpleLambda;
import cl.proxime.lambda.mocks.context.SimpleRequest;
import cl.proxime.lambda.mocks.context.SimpleResponse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LambdaContextTest {

    private final LambdaContext lambdaContext = new LambdaContext(
            new SimpleLambda(),
            new SimpleRequest(),
            new SimpleResponse()
    );

    @Test
    public void tryToBuildALambdaContextWithLambdaRequestAndResponse() {

        assertThat(lambdaContext, instanceOf(LambdaContext.class));
    }

    @Test
    public void tryToExecuteLambdaContext() {
        assertEquals("{\"test\":\"Okey\"}", this.lambdaContext.execute("{}"));
    }
}
