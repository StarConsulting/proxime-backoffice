package app.proxime.lambda.src.infrastructure.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;

import java.util.HashMap;
import java.util.Map;

public class CognitoAuthenticator {

    private AWSCognitoIdentityProvider identityProvider;
    private CognitoClientCredentials clientCredentials;

    public CognitoAuthenticator(
            AWSCognitoIdentityProvider identityProvider,
            CognitoClientCredentials clientCredentials
            ){
        this.identityProvider = identityProvider;
        this.clientCredentials = clientCredentials;
    }

    public AuthenticationResponse authenticateUser(String username, String password){

        return new AuthenticationResponse(fromTheResults(forTheRequestToCognito(withThisUserParams(username, password))));
    }

    private AuthenticationResultType fromTheResults(AdminInitiateAuthResult authResult){
        return authResult.getAuthenticationResult();
    }

    private AdminInitiateAuthResult forTheRequestToCognito(AdminInitiateAuthRequest authRequest){
        return identityProvider.adminInitiateAuth(authRequest);
    }

    private AdminInitiateAuthRequest withThisUserParams(String username, String password){
        Map<String,String> authParams = new HashMap<String,String>();
        authParams.put("USERNAME", username);
        authParams.put("PASSWORD", password);

        return buildAuthRequest(authParams);
    }
    private AdminInitiateAuthRequest buildAuthRequest(Map<String,String> authParams){

        return new AdminInitiateAuthRequest()
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withAuthParameters(authParams)
                .withClientId(clientCredentials.getClientId())
                .withUserPoolId(clientCredentials.getPoolId());
    }
}
