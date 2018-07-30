package cl.proxime.lambda.framework.exception;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class LambdaExceptionHandlerTest {


    LambdaExceptionHandler lambdaExceptionHandler = new LambdaExceptionHandler();

    @Test
    public void tryToThrowLambdaExceptionEmpty() {
        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    throw lambdaExceptionHandler.throwException();
                }
        );
        assertEquals("{\"id\":\"\",\"errorType\":\"\",\"message\":\"\",\"errors\":[]}",
                exceptionThrown.getMessage()
        );
    }

    @Test
    public void tryToThrowLambdaExceptionWithId() {

        lambdaExceptionHandler.setId("1");

        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    throw lambdaExceptionHandler.throwException();
                }
        );
        assertEquals("{\"id\":\"1\",\"errorType\":\"\",\"message\":\"\",\"errors\":[]}",
                exceptionThrown.getMessage()
        );
    }

    @Test
    public void tryToThrowLambdaExceptionWithErrorType() {
        lambdaExceptionHandler.setErrorType("Test error");

        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    throw lambdaExceptionHandler.throwException();
                }
        );

        assertEquals("{\"id\":\"\",\"errorType\":\"Test error\",\"message\":\"\",\"errors\":[]}",
                exceptionThrown.getMessage()
        );
    }

    @Test
    public void tryToThrowLambdaExceptionWithMessage() {
        lambdaExceptionHandler.setMessage("Error in the execution of TestLambda");

        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    throw lambdaExceptionHandler.throwException();
                }
        );

        assertEquals("{\"id\":\"\",\"errorType\":\"\",\"message\":\"Error in the execution of TestLambda\",\"errors\":[]}",
                exceptionThrown.getMessage()
        );
    }

    @Test
    public void tryToThrowLambdaExceptionWithErrorList() {
        List<String> errors = new ArrayList<>();
        errors.add("Error 1");
        errors.add("Error 2");
        lambdaExceptionHandler.setErrors(errors);
        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    throw lambdaExceptionHandler.throwException();
                }
        );

        assertEquals("{\"id\":\"\",\"errorType\":\"\",\"message\":\"\",\"errors\":[\"Error 1\",\"Error 2\"]}",
                exceptionThrown.getMessage()
        );
    }

    @Test
    public void tryToThrowLambdaExceptionAddingErrors() {
        lambdaExceptionHandler.addError("Error 1");
        lambdaExceptionHandler.addError("Error 2");

        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    throw lambdaExceptionHandler.throwException();
                }
        );

        assertEquals("{\"id\":\"\",\"errorType\":\"\",\"message\":\"\",\"errors\":[\"Error 1\",\"Error 2\"]}",
                exceptionThrown.getMessage()
        );
    }


    @Test
    public void tryToThrowLambdaExceptionWithFullBody() {
        lambdaExceptionHandler.setId("12");
        lambdaExceptionHandler.setErrorType("Test error");
        lambdaExceptionHandler.setMessage("Error in the execution of TestLambda");
        lambdaExceptionHandler.addError("Error 1");
        lambdaExceptionHandler.addError("Error 2");

        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    throw lambdaExceptionHandler.throwException();
                }
        );

        assertEquals("{" +
                        "\"id\":\"12\"," +
                        "\"errorType\":\"Test error\"," +
                        "\"message\":\"Error in the execution of TestLambda\"," +
                        "\"errors\":[" +
                        "\"Error 1\"," +
                        "\"Error 2\"" +
                        "]}",
                exceptionThrown.getMessage()
        );
    }
}
