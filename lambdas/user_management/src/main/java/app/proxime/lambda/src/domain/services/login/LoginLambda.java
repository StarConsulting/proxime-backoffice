package app.proxime.lambda.src.domain.services.login;

import app.proxime.lambda.ResponseMessages;
import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.context.ResponseInformation;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.infrastructure.cognito.AuthenticationResponse;
import app.proxime.lambda.src.infrastructure.cognito.CognitoClient;
import app.proxime.lambda.src.infrastructure.user.UserDynamoDBRepository;
import com.amazonaws.services.lambda.runtime.Context;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginLambda implements Lambda<LoginRequest, LoginResponse> {
    private UserDynamoDBRepository repository;

    public LoginLambda(UserDynamoDBRepository repository) {
        this.repository = repository;
    }

    public LoginResponse execute(LoginRequest request, Context context) throws LambdaException {

        /*User user = findUserToAuthenticate(request);
        if (userIsNull(user)) return mailError(context);

        if(user.passwordIsIncorrect(request.password)) return passwordError(context);

        */
        return authenticationToken(request.email, request.password);
    }

    private User findUserToAuthenticate(LoginRequest request) {
        return repository.getByField("email", request.email);
    }

    private boolean userIsNull(User user) {
        return user == null;
    }

    private LoginResponse mailError(Context context) {
        LoginResponse response = new LoginResponse();
        List<String> errors = new ArrayList();
        response.info = buildResponseInformation(ResponseMessages.EMAIL_IS_NOT_REGISTERED, errors, 1000L, context.getAwsRequestId());
        return response;
    }

    private LoginResponse passwordError(Context context) {
        LoginResponse response = new LoginResponse();
        List<String> errors = new ArrayList();
        errors.add("The entered credentials are incorrect");
        response.info = buildResponseInformation(ResponseMessages.PASSWORD_IS_INCORRECT, errors, 1000L, context.getAwsRequestId());
        return response;
    }

    private LoginResponse authenticationToken(String email, String password) {

        CognitoClient cognitoClient = new CognitoClient();
        AuthenticationResponse authenticationResponse = cognitoClient.authenticateUser(email, password);

        LoginResponse response = new LoginResponse();
        response.accessToken = authenticationResponse.getAccessToken();
        response.tokenDuration = authenticationResponse.getTokenDuration();


        return response;
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