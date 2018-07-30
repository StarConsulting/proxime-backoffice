package cl.proxime.lambda.framework.exception;

import cl.proxime.lambda.framework.parser.GsonParser;
import cl.proxime.lambda.framework.parser.Parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LambdaExceptionHandler {

    private String id;
    private String errorType;
    private String message;
    private List<String> errors;

    private Parser parser;

    public LambdaExceptionHandler() {
        this.id = "";
        this.errorType = "";
        this.message = "";
        this.errors = new ArrayList<>();
        this.parser = new Parser(new GsonParser());
    }

    public LambdaException throwException() {

        throw new LambdaException(buildExceptionMessage(
                this.id,
                this.errorType,
                this.message,
                this.errors
        ));
    }

    private String buildExceptionMessage(
            String id,
            String errorType,
            String message,
            List<String> errors
    ) {
        /*
            it is necessary to respect the order of these parameters
            Note: look for a way to have an unalterable default order
        */
        Map<String, Object> mappedException = new LinkedHashMap<>();
        mappedException.put("id", id);
        mappedException.put("errorType", errorType);
        mappedException.put("message", message);
        mappedException.put("errors", errors);


        return parser.mapToJson(mappedException);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }
}
