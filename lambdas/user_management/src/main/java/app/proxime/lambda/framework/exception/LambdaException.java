package app.proxime.lambda.framework.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LambdaException extends RuntimeException {

    private int errorId;
    private String errorMessage;
    private List<String> errors = new ArrayList<>();
    private String requestId;

    public LambdaException(String message) {
        super(message);
    }

    public LambdaException(int errorId, String errorMessage){
        this.errorId = errorId;
        this.errorMessage = errorMessage;
    }

    public int getErrorId() {
        return errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getRequestId() {
        return requestId;
    }

    public void addError(String error){
        this.errors.add(error);
    }
    public void setRequestId(String requestId){
        this.requestId = requestId;
    }
}
