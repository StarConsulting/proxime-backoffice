package app.proxime.lambda.src.infrastructure.cognito.register;

import app.proxime.lambda.src.domain.services.register.ExternalRegisterResponse;

import java.util.List;

public class CognitoRegisterResponse implements ExternalRegisterResponse {

    protected String successMessage;

    protected int errorId;
    protected String errorMessage;
    protected List<String> errorList;

    protected String requestId;

    protected boolean hasError;

    public static CognitoRegisterResponse onSuccess(String successMessage){
        CognitoRegisterResponse response = new CognitoRegisterResponse(successMessage);
        response.itHasNotErrors();
        return response;
    }

    private CognitoRegisterResponse(String successMessage){
        this.successMessage = successMessage;
    }

    private void itHasNotErrors(){
        this.hasError = false;
    }

    public static CognitoRegisterResponse onFailure(
            int errorId, String errorMessage, List<String> errorList, String requestId
    ){
        CognitoRegisterResponse response = new CognitoRegisterResponse(errorId, errorMessage, errorList, requestId);
        response.itHasErrors();
        return response;
    }

    private CognitoRegisterResponse(
            int errorId, String errorMessage, List<String> errorList, String requestId
    ){
        this.errorId = errorId;
        this.errorMessage = errorMessage;
        this.errorList = errorList;
        this.requestId = requestId;
    }

    private void itHasErrors(){
        this.hasError = true;
    }

    @Override
    public String getSuccessMessage() {
        return this.successMessage;
    }

    @Override
    public int getErrorId() {
        return this.errorId;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public List<String> getErrorList() {
        return this.errorList;
    }

    @Override
    public String getRequestId() {
        return this.requestId;
    }

    @Override
    public boolean hasError() {
        return this.hasError;
    }
}
