package app.proxime.lambda.framework.interceptor;

import app.proxime.lambda.framework.annotations.Intercept;
import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.exception.InternalErrorMessages;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.filters.Filter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class AnnotationFinder {

    public List<Filter> getFiltersRequired(Lambda lambda) throws LambdaException {
        List<Filter> filters = new ArrayList();
        for (String path : findInterceptAnnotations(lambda)) {
            filters.add(getFilterInstance(path));
        }
        return filters;
    }

    private List<String> findInterceptAnnotations(Lambda lambda) {
        Intercept[] intercepts = lambda.getClass().getAnnotationsByType(Intercept.class);
        List<String> filtersPaths = new ArrayList();

        for (Intercept intercept : intercepts) {
            filtersPaths.add(intercept.classPath());
        }

        return filtersPaths;
    }

    private Filter getFilterInstance(String path) throws LambdaException {
        try {
            return (Filter) Class.forName(path).getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LambdaException exception = new LambdaException(
                    InternalErrorMessages.FILTER_NOT_FOUND.getId(),
                    InternalErrorMessages.FILTER_NOT_FOUND.getMessage()
            );
            exception.addError("Error creating the filter "+path);

            throw exception;
        }
    }
}
