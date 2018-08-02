package app.proxime.lambda.framework.filter;

import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.filters.FilterChain;
import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.mocks.filter.SimpleFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterChainTest {

    @Test
    public void tryToApplyFilters() throws LambdaException {
        FilterChain filterChain = new FilterChain();
        filterChain.setFilter(new SimpleFilter());
        Input input = filterChain.doFilters(Input.fromString("{}"));
        assertEquals("Filtered", input.getInputString());
    }

}