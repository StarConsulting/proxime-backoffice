package app.proxime.lambda.framework.filters;

import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.input.Input;

public interface Filter {
    Input execute(Input input) throws LambdaException;
}
