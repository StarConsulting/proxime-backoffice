package app.proxime.lambda.src.domain.user;

import app.proxime.lambda.src.infrastructure.cognito.AuthenticationResponse;

public interface UserRepository <PersistenceModel>{

    void signUp(User user);

    AuthenticationResponse signin(String email, String password);
}
