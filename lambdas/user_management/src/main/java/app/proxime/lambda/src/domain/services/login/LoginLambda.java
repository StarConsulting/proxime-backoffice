package app.proxime.lambda.src.domain.services.login;

import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.src.domain.user.UserRepository;
import app.proxime.lambda.src.infrastructure.cognito.AuthenticationResponse;
import com.amazonaws.services.lambda.runtime.Context;

public class LoginLambda implements Lambda<LoginRequest, LoginResponse> {

    private UserRepository repository;

    public LoginLambda(
        UserRepository repository
    ){
        this.repository = repository;
    }

    public LoginResponse execute(LoginRequest request, Context context) throws LambdaException {

        return authenticationToken(repository.signin(request.username, request.password));
    }


    private LoginResponse authenticationToken(AuthenticationResponse authenticationResponse) {

        LoginResponse response = new LoginResponse();
        response.accessToken = authenticationResponse.getAccessToken();
        response.tokenDuration = authenticationResponse.getTokenDuration();


        return response;
    }
}