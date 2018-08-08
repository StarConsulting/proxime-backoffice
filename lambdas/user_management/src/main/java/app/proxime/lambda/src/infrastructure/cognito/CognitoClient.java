package app.proxime.lambda.src.infrastructure.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;

public class CognitoClient {

    private CognitoAuthenticator cognitoAuthenticator;

    public CognitoClient(){
        AWSCognitoIdentityProvider identityProvider = AWSCognitoIdentityProviderClient
                .builder()
                .withRegion("us-east-2")
                .build();

        this.cognitoAuthenticator = new CognitoAuthenticator(
                identityProvider,
                new CognitoClientCredentials()
        );
    }

    public AuthenticationResponse authenticateUser(String email, String password){

    return cognitoAuthenticator.authenticateUser(email,password);
        }


}
