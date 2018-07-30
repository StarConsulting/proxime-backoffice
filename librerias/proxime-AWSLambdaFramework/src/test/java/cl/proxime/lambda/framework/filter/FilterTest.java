package cl.proxime.lambda.framework.filter;

import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.framework.input.Input;
import cl.proxime.lambda.mocks.filter.FilterWithError;
import cl.proxime.lambda.mocks.filter.SimpleFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilterTest {

    @Test
    public void tryToExecuteFilter() {
        SimpleFilter filter = new SimpleFilter();
        Input input = filter.execute(Input.fromString("{"));
        assertEquals("Filtered", input.getInputString());
    }

    @Test
    public void tryToThrowLambdaException() throws LambdaException {
        FilterWithError filter = new FilterWithError();
        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    filter.execute(Input.fromString("{"));
                }
        );

        assertEquals("{" +
                "\"id\":\"\"," +
                "\"errorType\":\"\"," +
                "\"message\":\"Filter error\"," +
                "\"errors\":[]" +
                "}", exceptionThrown.getMessage());
    }
}
