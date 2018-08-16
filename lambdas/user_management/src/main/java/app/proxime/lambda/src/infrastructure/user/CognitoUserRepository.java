package app.proxime.lambda.src.infrastructure.user;

import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.domain.user.UserRepository;
import app.proxime.lambda.src.infrastructure.cognito.CognitoClient;
import app.proxime.lambda.src.domain.services.register.ExternalRegisterResponse;
import app.proxime.lambda.src.infrastructure.cognito.authentication.CognitoAuthenticationResponse;

public class CognitoUserRepository implements UserRepository {

    private CognitoClient cognitoClient;

    public CognitoUserRepository(){
        cognitoClient = new CognitoClient();
    }

    @Override
    public ExternalRegisterResponse signUp(User user) {


        return cognitoClient.registerUser(
                user.getName(),
                user.getFamilyName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone()
        );
    }

    @Override
    public CognitoAuthenticationResponse signin(String username, String password) {

        return cognitoClient.authenticateUser(username,password);

    }
}
