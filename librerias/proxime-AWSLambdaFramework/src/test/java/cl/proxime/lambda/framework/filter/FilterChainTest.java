package cl.proxime.lambda.framework.filter;

import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.framework.filters.FilterChain;
import cl.proxime.lambda.framework.input.Input;
import cl.proxime.lambda.mocks.filter.SimpleFilter;
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