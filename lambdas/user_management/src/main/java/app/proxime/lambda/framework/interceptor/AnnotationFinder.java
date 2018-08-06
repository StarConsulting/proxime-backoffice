package app.proxime.lambda.framework.interceptor;

import app.proxime.lambda.framework.annotations.Intercept;
import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.exception.LambdaExceptionHandler;
import app.proxime.lambda.framework.filters.Filter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class AnnotationFinder {

    private LambdaExceptionHandler lambdaExceptionHandler;

    public AnnotationFinder(){
        this.lambdaExceptionHandler = new LambdaExceptionHandler();
    }

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
            lambdaExceptionHandler.setId("15");
            lambdaExceptionHandler.setErrorType("Filter search");
            lambdaExceptionHandler.setMessage("An error has occurred in the filter creation");
            lambdaExceptionHandler.addError("Error creating the filter "+path);

            throw lambdaExceptionHandler.throwException();

        }
    }
}
