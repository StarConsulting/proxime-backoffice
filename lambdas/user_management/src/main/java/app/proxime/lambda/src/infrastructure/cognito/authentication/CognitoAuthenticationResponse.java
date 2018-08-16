package app.proxime.lambda.src.infrastructure.cognito.authentication;

import app.proxime.lambda.src.domain.services.authentication.ExternalAuthenticationResponse;
import java.util.List;

public class CognitoAuthenticationResponse implements ExternalAuthenticationResponse {

    protected String accessToken;
    protected long tokenDuration;

    protected int errorId;
    protected String errorMessage;
    protected List<String> errorList;

    protected String requestId;

    protected boolean hasError;

    public static CognitoAuthenticationResponse onSuccess(String accessToken, long tokenDuration){
        CognitoAuthenticationResponse cognitoAuthenticationResponse = new CognitoAuthenticationResponse(accessToken, tokenDuration);
        cognitoAuthenticationResponse.itHasNotErrors();
        return cognitoAuthenticationResponse;
    }

    private CognitoAuthenticationResponse(String accessToken, long tokenDuration){
        this.accessToken = accessToken;
        this.tokenDuration = tokenDuration;
    }

    private void itHasNotErrors(){
        this.hasError = false;
    }

    public static CognitoAuthenticationResponse onFailure(
            int errorId, String errorMessage, List<String> errorList, String requestId
    ){
        CognitoAuthenticationResponse cognitoAuthenticationResponse = new CognitoAuthenticationResponse(
                errorId, errorMessage, errorList, requestId
        );
        cognitoAuthenticationResponse.itHasErrors();
        return cognitoAuthenticationResponse;
    }

    private CognitoAuthenticationResponse(int errorId, String errorMessage, List<String> errorList, String requestId) {
        this.errorId = errorId;
        this.errorMessage = errorMessage;
        this.errorList = errorList;
        this.requestId = requestId;
    }

    private void itHasErrors(){
        this.hasError = true;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getTokenDuration() {
        return tokenDuration;
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
