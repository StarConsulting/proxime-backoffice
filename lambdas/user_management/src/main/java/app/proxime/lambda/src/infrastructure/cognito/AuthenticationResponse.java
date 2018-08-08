package app.proxime.lambda.src.infrastructure.cognito;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;

public class AuthenticationResponse {

    private String idToken = "";
    private String accessToken = "";
    private String refreshToken = "";
    private String tokenType = "";

    private int tokenDuration = 0;

    public AuthenticationResponse(
            AuthenticationResultType authenticationResult
    ){
        setParams(authenticationResult);
    }

    private void setParams(AuthenticationResultType authenticationResult){
        this.idToken = authenticationResult.getIdToken();
        this.accessToken = authenticationResult.getAccessToken();
        this.refreshToken = authenticationResult.getRefreshToken();
        this.tokenType =  authenticationResult.getTokenType();
        this.tokenDuration = authenticationResult.getExpiresIn();
    }

    public String getIdToken() {
        return idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getTokenDuration() {
        return tokenDuration;
    }
}
