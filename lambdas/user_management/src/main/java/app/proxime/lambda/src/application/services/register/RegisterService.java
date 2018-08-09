package app.proxime.lambda.src.application.services.register;

import app.proxime.lambda.framework.context.LambdaContext;
import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.framework.interceptor.Interceptor;
import app.proxime.lambda.framework.service.BaseService;
import app.proxime.lambda.src.domain.services.register.RegisterLambda;
import app.proxime.lambda.src.domain.services.register.RegisterRequest;
import app.proxime.lambda.src.domain.services.register.RegisterResponse;
import app.proxime.lambda.src.infrastructure.user.CognitoUserRepository;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.OutputStream;

public class RegisterService extends BaseService {
    @Override
    public void execute(
            Input input,
            OutputStream outputStream,
            Context context
    ) throws IOException {


        RegisterLambda registerLambda = new RegisterLambda(
                new CognitoUserRepository()
        );
        RegisterRequest registerRequest = new RegisterRequest();
        RegisterResponse registerResponse = new RegisterResponse();




        Interceptor interceptor = new Interceptor();
        LambdaContext lambdaContext = interceptor.buildLambdaContext(
                registerLambda,
                registerRequest,
                registerResponse
        );


        String response = interceptor.executeContext(
                lambdaContext,
                input.getInputString(),
                context
        );
        outputStream.write(response.getBytes());}
}
