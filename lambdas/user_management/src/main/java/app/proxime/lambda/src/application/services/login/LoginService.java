package app.proxime.lambda.src.application.services.login;

import app.proxime.lambda.framework.context.LambdaContext;
import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.framework.interceptor.Interceptor;
import app.proxime.lambda.framework.service.BaseService;
import app.proxime.lambda.src.domain.services.login.LoginLambda;
import app.proxime.lambda.src.domain.services.login.LoginRequest;
import app.proxime.lambda.src.domain.services.login.LoginResponse;
import app.proxime.lambda.src.infrastructure.user.CognitoUserRepository;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.OutputStream;

public class LoginService extends BaseService {
    @Override
    public void execute(Input input, OutputStream outputStream, Context context) throws IOException {

        LoginLambda loginLambda = new LoginLambda(
                new CognitoUserRepository()
        );
        LoginRequest loginRequest = new LoginRequest();
        LoginResponse loginResponse = new LoginResponse();




        Interceptor interceptor = new Interceptor();
        LambdaContext lambdaContext = interceptor.buildLambdaContext(
                loginLambda,
                loginRequest,
                loginResponse
        );



        String response = interceptor.executeContext(
                lambdaContext,
                input.getInputString(),
                context
        );
        outputStream.write(response.getBytes());
    }
}
