package cl.proxime.lambda.framework.interceptor;

import cl.proxime.lambda.framework.context.LambdaContext;
import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.framework.input.Input;
import cl.proxime.lambda.mocks.context.LambdaWithFilter;
import cl.proxime.lambda.mocks.context.SimpleLambda;
import cl.proxime.lambda.mocks.context.SimpleRequest;
import cl.proxime.lambda.mocks.context.SimpleResponse;
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
    public void tryToThrowLambdaExceptionForInvalidInputJson() {
        final String inputString = "invalid input";
        Input input = Input.fromString(inputString);
        Interceptor interceptor = new Interceptor();
        LambdaContext lambdaContext = interceptor.buildLambdaContext(
                new LambdaWithFilter(),
                new SimpleRequest(),
                new SimpleResponse()
        );

        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    interceptor.applyFiltersToInput(lambdaContext, input);
                }
        );

        assertEquals("{" +
                "\"id\":\"10\"," +
                "\"errorType\":\"Invalid input\"," +
                "\"message\":\"The input format is incorrect\"," +
                "\"errors\":[\"Must be a valid Json\"]" +
                "}", exceptionThrown.getMessage());
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
