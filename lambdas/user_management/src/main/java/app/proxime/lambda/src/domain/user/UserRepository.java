package app.proxime.lambda.src.domain.user;

import app.proxime.lambda.src.domain.services.authentication.ExternalAuthenticationResponse;
import app.proxime.lambda.src.domain.services.register.ExternalRegisterResponse;

public interface UserRepository{

    ExternalRegisterResponse signUp(User user);

    ExternalAuthenticationResponse signin(String email, String password);
}
