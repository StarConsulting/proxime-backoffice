package app.proxime.lambda.framework.filters;

import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.input.Input;

public class FilterManager {

    private FilterChain filterChain;

    public FilterManager() {
        this.filterChain = new FilterChain();
    }

    public void setFilter(Filter filter) {
        this.filterChain.setFilter(filter);
    }

    public Input doFilters(Input input) throws LambdaException {
        return filterChain.doFilters(input);
    }
}
