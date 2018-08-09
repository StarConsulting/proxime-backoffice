package app.proxime.lambda.src.domain.services.register;

import app.proxime.lambda.ResponseMessages;
import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.context.ResponseInformation;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.domain.user.UserRepository;
import app.proxime.lambda.src.infrastructure.cognito.CognitoClient;
import com.amazonaws.services.lambda.runtime.Context;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RegisterLambda implements Lambda<RegisterRequest, RegisterResponse> {

    private UserRepository repository;

    public RegisterLambda(UserRepository repository) {

        this.repository = repository;
    }

    public RegisterResponse execute(RegisterRequest request, Context context) throws LambdaException {

        User user = buildUserWith(request);

        repository.signUp(user);

        return successfulRegister();

    }

    private RegisterResponse successfulRegister() {
        RegisterResponse response = new RegisterResponse();
        response.response = "Successful register";
        return response;
    }

    private User buildUserWith(RegisterRequest request) {
        return new User(
                request.name,
                request.familyName,
                request.username,
                request.email,
                request.password,
                request.phone
        );
    }
}