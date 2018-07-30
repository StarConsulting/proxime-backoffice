package cl.proxime.lambda.framework.filter;

import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.framework.filters.FilterManager;
import cl.proxime.lambda.framework.input.Input;
import cl.proxime.lambda.mocks.filter.SimpleFilter;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterManagerTest {

    @Test
    public void tryToBuildAFilterManager() {

        assertThat(new FilterManager(), instanceOf(FilterManager.class));
    }

    @Test
    public void tryToApplyFilters() throws LambdaException {
        FilterManager filterManager = new FilterManager();
        filterManager.setFilter(new SimpleFilter());
        Input input = filterManager.doFilters(Input.fromString("{}"));
        assertEquals("Filtered", input.getInputString());
    }
}
