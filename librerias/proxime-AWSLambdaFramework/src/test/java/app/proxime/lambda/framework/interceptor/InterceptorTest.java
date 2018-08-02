package app.proxime.lambda.framework.interceptor;

import app.proxime.lambda.framework.context.LambdaContext;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.mocks.context.LambdaWithFilter;
import app.proxime.lambda.mocks.context.SimpleLambda;
import app.proxime.lambda.mocks.context.SimpleRequest;
import app.proxime.lambda.mocks.context.SimpleResponse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterceptorTest {

    @Test
    public void tryToGetAnInterceptorObject() {

        Interceptor interceptor = new Interceptor();

        assertThat(interceptor, instanceOf(Interceptor.class));
    }

    @Test
    public void tryToBuildLambdaContext() {
        Interceptor interceptor = new Interceptor();
        LambdaContext lambdaContext = interceptor.buildLambdaContext(
                new SimpleLambda(),
                new SimpleRequest(),
                new SimpleResponse()
        );

        assertThat(lambdaContext, instanceOf(LambdaContext.class));
    }

    @Test
    public void tryToFindAnnotationsInLambda() {
        SimpleLambda lambda = new SimpleLambda();
    }

    @Test
    public void tryToApplyFiltersToInput() throws LambdaException{
        String inputString = "{}";
        Input input = Input.fromString(inputString);
        Interceptor interceptor = new Interceptor();

        LambdaContext lambdaContext = interceptor.buildLambdaContext(
                new LambdaWithFilter(),
                new SimpleRequest(),
                new SimpleResponse()
        );
        Input response = interceptor.applyFiltersToInput(lambdaContext, input);

        assertEquals("Filtered", response.getInputString());
    }

    @Test
    public void tryToExecuteLambdaContext() {
        String input = "{}";
        Interceptor interceptor = new Interceptor();
        LambdaContext lambdaContext = interceptor.buildLambdaContext(
                new SimpleLambda(),
                new SimpleRequest(),
                new SimpleResponse()
        );
        String response = interceptor.executeContext(lambdaContext, input);

        assertEquals("{\"test\":\"Okey\"}", response);
    }
}
