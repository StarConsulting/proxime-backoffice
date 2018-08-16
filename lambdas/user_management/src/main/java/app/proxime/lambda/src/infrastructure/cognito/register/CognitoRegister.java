package app.proxime.lambda.src.infrastructure.cognito.register;

import app.proxime.lambda.ResponseMessages;
import app.proxime.lambda.src.infrastructure.cognito.ClientCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;

import java.util.ArrayList;
import java.util.List;

public class CognitoRegister {


    private AWSCognitoIdentityProvider identityProvider;
    private ClientCredentials clientCredentials;

    public CognitoRegister(
            AWSCognitoIdentityProvider identityProvider,
            ClientCredentials clientCredentials
    ){
        this.identityProvider = identityProvider;
        this.clientCredentials = clientCredentials;
    }

    public CognitoRegisterResponse register(
            String name,
            String familyName,
            String username,
            String email,
            String password,
            String phone
    ){
        List<AttributeType> attributes = new ArrayList<>();

        AttributeType nameAttribute = buildAttribute("name", name);
        AttributeType familyNameAttribute = buildAttribute("family_name", familyName);
        AttributeType emailAttribute = buildAttribute("email", email);
        AttributeType phoneAttribute = buildAttribute("phone_number",phone);

        attributes.add(nameAttribute);
        attributes.add(familyNameAttribute);
        attributes.add(emailAttribute);
        attributes.add(phoneAttribute);

        SignUpRequest signUpRequest = new SignUpRequest();

        signUpRequest.setClientId(clientCredentials.getClientId());
        signUpRequest.setUsername(username);
        signUpRequest.setPassword(password);
        signUpRequest.setUserAttributes(attributes);

        try{
            identityProvider.signUp(signUpRequest);

            return CognitoRegisterResponse.onSuccess("Register successfully");

        }catch(UsernameExistsException ex){
            return CognitoRegisterResponse.onFailure(
                    ResponseMessages.USERNAME_IS_ALREADY_IN_USE.getId(),
                    ResponseMessages.USERNAME_IS_ALREADY_IN_USE.getMessage(),
                    new ArrayList<>(),
                    ex.getRequestId()
            );
        }
    }

    private AttributeType buildAttribute(String name, String value){
        return new AttributeType().withName(name).withValue(value);
    }

}
