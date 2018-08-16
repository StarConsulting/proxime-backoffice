package app.proxime.lambda.src.application.services.login;

import app.proxime.lambda.framework.context.LambdaContext;
import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.framework.interceptor.Interceptor;
import app.proxime.lambda.framework.service.BaseService;
import app.proxime.lambda.src.domain.services.authentication.AuthenticationLambda;
import app.proxime.lambda.src.domain.services.authentication.AuthenticationRequest;
import app.proxime.lambda.src.domain.services.authentication.AuthenticationResponse;
import app.proxime.lambda.src.infrastructure.user.CognitoUserRepository;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.OutputStream;

public class LoginService extends BaseService {
    @Override
    public void execute(Input input, OutputStream outputStream, Context context) throws IOException {

        AuthenticationLambda authenticationLambda = new AuthenticationLambda(
                new CognitoUserRepository()
        );
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();




        Interceptor interceptor = new Interceptor();
        LambdaContext lambdaContext = interceptor.buildLambdaContext(
                authenticationLambda, authenticationRequest, authenticationResponse
        );



        String response = interceptor.executeContext(
                lambdaContext, input.getInputString(), context
        );
        outputStream.write(response.getBytes());
    }
}
