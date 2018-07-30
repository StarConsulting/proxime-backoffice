package cl.proxime.lambda.mocks.filter;

import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.framework.exception.LambdaExceptionHandler;
import cl.proxime.lambda.framework.filters.Filter;
import cl.proxime.lambda.framework.input.Input;

public class FilterWithError implements Filter {
    @Override
    public Input execute(Input input) throws LambdaException {
        LambdaExceptionHandler lambdaExceptionHandler = new LambdaExceptionHandler();
        lambdaExceptionHandler.setMessage("Filter error");
        lambdaExceptionHandler.throwException();
        return Input.fromString("nothing");
    }
}
