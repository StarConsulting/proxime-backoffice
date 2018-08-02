package app.proxime.lambda.framework.interceptor;

import app.proxime.lambda.framework.context.BaseRequest;
import app.proxime.lambda.framework.context.BaseResponse;
import app.proxime.lambda.framework.context.LambdaContext;
import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.exception.LambdaExceptionHandler;
import app.proxime.lambda.framework.filters.Filter;
import app.proxime.lambda.framework.filters.FilterManager;
import app.proxime.lambda.framework.input.Input;

public class Interceptor {

    private AnnotationFinder annotationFinder;
    private FilterManager filterManager;
    private LambdaExceptionHandler lambdaExceptionHandler;

    public Interceptor() {
        this.annotationFinder = new AnnotationFinder();
        this.filterManager = new FilterManager();
        this.lambdaExceptionHandler = new LambdaExceptionHandler();
    }

    public LambdaContext buildLambdaContext(Lambda lambda, BaseRequest request, BaseResponse response) {
        return new LambdaContext(lambda, request, response);
    }

    public Input applyFiltersToInput(LambdaContext lambdaContext, Input input) throws LambdaException {

        for (Filter filter : annotationFinder.getFiltersRequired(lambdaContext.getLambda())) {
            filterManager.setFilter(filter);
        }
        return filterManager.doFilters(input);
    }

    public String executeContext(LambdaContext lambdaContext, String input) throws LambdaException{


        return lambdaContext.execute(input);
    }
}
