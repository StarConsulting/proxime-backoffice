package app.proxime.lambda.src.infrastructure.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;

public class CognitoClient {

    private CognitoAuthenticator cognitoAuthenticator;
    private CognitoRegister cognitoRegister;

    public CognitoClient(){
        AWSCognitoIdentityProvider identityProvider = AWSCognitoIdentityProviderClient
                .builder()
                .withRegion("us-east-2")
                .build();

        this.cognitoAuthenticator = new CognitoAuthenticator(
                identityProvider,
                new ClientCredentials()
        );

        this.cognitoRegister = new CognitoRegister(
            identityProvider,
            new ClientCredentials()
        );
    }

    public AuthenticationResponse authenticateUser(String username, String password){

        return cognitoAuthenticator.authenticateUser(username,password);
    }

    public void registerUser(
            String name,
            String familyName,
            String username,
            String email,
            String password,
            String phone
    ){

        cognitoRegister.register(name, familyName, username, email, password, phone);
    }


}
