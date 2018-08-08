package app.proxime.lambda.src.domain.services.register;

import app.proxime.lambda.ResponseMessages;
import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.context.ResponseInformation;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.domain.user.UserRepository;
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
        User userFound = findUsername(request.username);

        if (userExist(userFound)) return usernameError(userFound, context);

        User userWithEmail = findEmail(request.email);

        if(userExist(userWithEmail)) return emailError(userWithEmail, context);

        registerUser(buildUserWith(request));

        return successfulRegister();

    }

    private User findUsername(String username) {
        return repository.getByField("username", username);
    }

    private boolean userExist(User user) {
        return user != null;
    }

    private RegisterResponse usernameError(User user, Context context) {
        RegisterResponse response = new RegisterResponse();
        List<String> errors = new ArrayList();
        errors.add(user.getUsername() + " in use");
        response.info = buildResponseInformation(
                ResponseMessages.USERNAME_IS_ALREADY_IN_USE,
                errors,
                1000L,
                context.getAwsRequestId()
        );
        return response;
    }

    private User findEmail(String email) {
        return repository.getByField("email", email);
    }

    private RegisterResponse emailError(User user, Context context) {
        RegisterResponse response = new RegisterResponse();
        List<String> errors = new ArrayList();
        errors.add(user.getEmail() + " in use");
        response.info = buildResponseInformation(
                ResponseMessages.EMAIL_IS_ALREADY_IN_USE,
                errors,
                1000L,
                context.getAwsRequestId()
        );
        return response;
    }

    private void registerUser(User user) {
        repository.insertOrUpdate(user);
    }

    private RegisterResponse successfulRegister() {
        RegisterResponse response = new RegisterResponse();
        response.response = "Successful register";
        return response;
    }

    private User buildUserWith(RegisterRequest request) {
        return new User(
                UUID.randomUUID().toString(),
                request.name,
                request.username,
                request.email,
                request.password,
                request.phone,
                request.birthdate
        );
    }

    private ResponseInformation buildResponseInformation(ResponseMessages responseMessage, List<String> errors, long duration, String transactionId) {
        ResponseInformation information = new ResponseInformation();
        information.code = responseMessage.getCode();
        information.message = responseMessage.getMessage();
        information.errors = errors;
        information.date = new Date();
        information.duration = 1000;
        information.transactionId = transactionId;
        return information;
    }
}