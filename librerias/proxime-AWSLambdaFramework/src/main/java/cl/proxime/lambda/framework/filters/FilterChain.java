package cl.proxime.lambda.framework.filters;

import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.framework.input.Input;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {

    private List<Filter> filters;

    public FilterChain() {
        this.filters = new ArrayList();
    }

    public void setFilter(Filter filter) {
        this.filters.add(filter);
    }

    public Input doFilters(Input input) throws LambdaException {
        for (Filter filter : this.filters) {
            input = filter.execute(input);
        }
        return input;
    }
}
