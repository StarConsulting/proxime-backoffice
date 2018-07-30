package cl.proxime.lambda.framework.context;

import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.mocks.context.LambdaWithException;
import cl.proxime.lambda.mocks.context.SimpleLambda;
import cl.proxime.lambda.mocks.context.SimpleRequest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LambdaTest {

    @Test
    public void tryToGetBaseResponseWhenExecuteLambda() {
        SimpleLambda lambda = new SimpleLambda();
        assertThat(lambda.execute(new SimpleRequest()),
                instanceOf(BaseResponse.class)
        );
    }

    @Test
    public void tryToThrowLambdaException(){
        LambdaWithException lambda = new LambdaWithException();

        LambdaException thrownException = assertThrows(LambdaException.class,
                () -> {
                    lambda.execute(new SimpleRequest());
                }
                );

        assertEquals("{" +
                "\"id\":\"\"," +
                "\"errorType\":\"\"," +
                "\"message\":\"Test message\"," +
                "\"errors\":[]" +
                "}", thrownException.getMessage());
    }
}
