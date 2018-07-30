package cl.proxime.lambda.framework.interceptor;

import cl.proxime.lambda.framework.context.BaseRequest;
import cl.proxime.lambda.framework.context.BaseResponse;
import cl.proxime.lambda.framework.context.LambdaContext;
import cl.proxime.lambda.framework.context.Lambda;
import cl.proxime.lambda.framework.exception.LambdaException;
import cl.proxime.lambda.framework.exception.LambdaExceptionHandler;
import cl.proxime.lambda.framework.filters.Filter;
import cl.proxime.lambda.framework.filters.FilterManager;
import cl.proxime.lambda.framework.input.Input;

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

        if (!input.isValid()) {
            throw buildLambdaException();
        }

        for (Filter filter : annotationFinder.getFiltersRequired(lambdaContext.getLambda())) {
            filterManager.setFilter(filter);
        }
        return filterManager.doFilters(input);
    }

    private LambdaException buildLambdaException(){
        lambdaExceptionHandler.setId("10");
        lambdaExceptionHandler.setErrorType("Invalid input");
        lambdaExceptionHandler.setMessage("The input format is incorrect");
        lambdaExceptionHandler.addError("Must be a valid Json");
        return lambdaExceptionHandler.throwException();
    }

    public String executeContext(LambdaContext lambdaContext, String input) throws LambdaException{

        return lambdaContext.execute(input);
    }
}
