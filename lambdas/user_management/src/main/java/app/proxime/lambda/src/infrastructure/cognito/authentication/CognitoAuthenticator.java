package app.proxime.lambda.src.infrastructure.cognito.authentication;

import app.proxime.lambda.ResponseMessages;
import app.proxime.lambda.src.infrastructure.cognito.ClientCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CognitoAuthenticator {

    private AWSCognitoIdentityProvider identityProvider;
    private ClientCredentials clientCredentials;

    public CognitoAuthenticator(
            AWSCognitoIdentityProvider identityProvider,
            ClientCredentials clientCredentials
            ){
        this.identityProvider = identityProvider;
        this.clientCredentials = clientCredentials;
    }

    public CognitoAuthenticationResponse authenticateUser(String username, String password){

        try{

            AuthenticationResultType response = fromTheResults(forTheRequestToCognito(withThisUserParams(username, password)));

            return CognitoAuthenticationResponse.onSuccess(response.getAccessToken(),response.getExpiresIn());

        }catch(UserNotFoundException ex){
            return CognitoAuthenticationResponse.onFailure(
                    ResponseMessages.USER_NOT_EXIST.getId(),
                    ResponseMessages.USER_NOT_EXIST.getMessage(),
                    new ArrayList<>(),
                    ex.getRequestId()
            );
        }catch (UserNotConfirmedException ex){
            return CognitoAuthenticationResponse.onFailure(
                    ResponseMessages.USER_NOT_CONFIRMED.getId(),
                    ResponseMessages.USER_NOT_CONFIRMED.getMessage(),
                    new ArrayList<>(),
                    ex.getRequestId()
            );
        }catch (NotAuthorizedException ex){
            return CognitoAuthenticationResponse.onFailure(
                ResponseMessages.PASSWORD_IS_INCORRECT.getId(),
                ResponseMessages.PASSWORD_IS_INCORRECT.getMessage(),
                new ArrayList<>(),
                ex.getRequestId()
            );
        }
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
