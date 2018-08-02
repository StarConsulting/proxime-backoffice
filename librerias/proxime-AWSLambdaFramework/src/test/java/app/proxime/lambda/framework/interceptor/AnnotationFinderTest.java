package app.proxime.lambda.framework.interceptor;

import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.filters.Filter;
import app.proxime.lambda.mocks.context.LambdaWithErrorInterceptRequired;
import app.proxime.lambda.mocks.context.LambdaWithInterceptRequired;
import app.proxime.lambda.mocks.filter.SimpleFilter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnnotationFinderTest {

    @Test
    public void tryToCreateFiltersFromInterceptAnnotationsPresentsInLambda() throws LambdaException {
        LambdaWithInterceptRequired lambda = new LambdaWithInterceptRequired();
        AnnotationFinder annotationFinder = new AnnotationFinder();
        List<Filter> createdFilters = annotationFinder.getFiltersRequired(lambda);

        assertThat(createdFilters.get(0), instanceOf(SimpleFilter.class));
    }

    @Test
    public void tryToGetLambdaExceptionInMissingFilter() throws LambdaException {
        LambdaWithErrorInterceptRequired lambda = new LambdaWithErrorInterceptRequired();
        AnnotationFinder annotationFinder = new AnnotationFinder();

        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                () -> {
                    annotationFinder.getFiltersRequired(lambda);
                }
        );

        assertEquals("{" +
                "\"id\":\"15\"," +
                "\"errorType\":\"Filter search\"," +
                "\"message\":\"An error has occurred in the filter creation\"," +
                "\"errors\":[\"Error creating the filter badPath\"]" +
                "}", exceptionThrown.getMessage());
    }
}
