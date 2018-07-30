package cl.proxime.lambda.framework.filters;

import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.framework.input.Input;

public interface Filter {
    Input execute(Input input) throws LambdaException;
}
