package app.proxime.lambda.src.infrastructure.user;

import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.domain.user.UserRepository;
import app.proxime.lambda.src.infrastructure.cognito.AuthenticationResponse;
import app.proxime.lambda.src.infrastructure.cognito.CognitoClient;

public class CognitoUserRepository implements UserRepository {

    private CognitoClient cognitoClient;

    public CognitoUserRepository(){
        cognitoClient = new CognitoClient();
    }

    @Override
    public void signUp(User user) {

        cognitoClient.registerUser(
                user.getName(),
                user.getFamilyName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone()
        );
    }

    @Override
    public AuthenticationResponse signin(String username, String password) {

        return cognitoClient.authenticateUser(username, password);

    }


}
