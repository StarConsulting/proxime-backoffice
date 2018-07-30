package cl.proxime.lambda.mocks.filter;

import cl.proxime.lambda.framework.filters.Filter;
import cl.proxime.lambda.framework.input.Input;

public class SimpleFilter implements Filter {
    @Override
    public Input execute(Input input) {
        return Input.fromString("Filtered");
    }
}
