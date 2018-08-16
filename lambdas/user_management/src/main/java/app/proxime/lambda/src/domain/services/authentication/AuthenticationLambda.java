package app.proxime.lambda.src.domain.services.authentication;

import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.context.ResponseInformation;
import app.proxime.lambda.src.domain.user.UserRepository;
import com.amazonaws.services.lambda.runtime.Context;

public class AuthenticationLambda implements Lambda<AuthenticationRequest, AuthenticationResponse> {

    private UserRepository repository;

    public AuthenticationLambda(
        UserRepository repository
    ){
        this.repository = repository;
    }

    public AuthenticationResponse execute(AuthenticationRequest request, Context context){

        ExternalAuthenticationResponse authenticationResponse = repository.signin(request.username, request.password);

        if (authenticationResponse.hasError())  return responseForError(authenticationResponse);

        return responseForSuccess(repository.signin(request.username, request.password));
    }


    /*
    *   Methods for errors building
    * */
    private AuthenticationResponse responseForError(ExternalAuthenticationResponse authenticationResponse){

        AuthenticationResponse response = new AuthenticationResponse();
        ResponseInformation information = new ResponseInformation();
        information.id = authenticationResponse.getErrorId();
        information.message = authenticationResponse.getErrorMessage();
        information.errors = authenticationResponse.getErrorList();
        information.transactionId = authenticationResponse.getRequestId();

        response.info = information;

        return response;
    }

    private AuthenticationResponse responseForSuccess(ExternalAuthenticationResponse authenticationResponse) {

        AuthenticationResponse response = new AuthenticationResponse();
        response.accessToken = authenticationResponse.getAccessToken();
        response.tokenDuration = authenticationResponse.getTokenDuration();

        return response;
    }
}