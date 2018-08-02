package app.proxime.lambda.mocks.filter;

import app.proxime.lambda.framework.filters.Filter;
import app.proxime.lambda.framework.input.Input;

public class SimpleFilter implements Filter {
    @Override
    public Input execute(Input input) {
        return Input.fromString("Filtered");
    }
}
