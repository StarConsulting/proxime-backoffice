package app.proxime.lambda.mocks.filter;

import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.exception.LambdaExceptionHandler;
import app.proxime.lambda.framework.filters.Filter;
import app.proxime.lambda.framework.input.Input;

public class FilterWithError implements Filter {
    @Override
    public Input execute(Input input) throws LambdaException {
        LambdaExceptionHandler lambdaExceptionHandler = new LambdaExceptionHandler();
        lambdaExceptionHandler.setMessage("Filter error");
        lambdaExceptionHandler.throwException();
        return Input.fromString("nothing");
    }
}
