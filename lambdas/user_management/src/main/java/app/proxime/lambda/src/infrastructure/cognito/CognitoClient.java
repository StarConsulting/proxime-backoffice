package app.proxime.lambda.src.infrastructure.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;

public class CognitoClient {

    private CognitoAuthenticator cognitoAuthenticator;

    public CognitoClient(){
        this.cognitoAuthenticator = new CognitoAuthenticator(
                new AWSCognitoIdentityProviderClient(),
                new CognitoClientCredentials()
        );
    }

    public AuthenticationResponse authenticateUser(String email, String password){
        return cognitoAuthenticator.authenticateUser(email,password);
    }


}
