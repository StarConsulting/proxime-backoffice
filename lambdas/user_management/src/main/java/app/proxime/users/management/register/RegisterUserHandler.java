package app.proxime.users.management.register;

import cl.proxime.lambda.framework.context.Lambda;
import cl.proxime.lambda.framework.exception.LambdaException;

public class RegisterUserHandler implements Lambda<RegisterRequest,RegisterResponse> {

    @Override
    public RegisterResponse execute(RegisterRequest request) throws LambdaException {
        RegisterResponse response=new RegisterResponse();
        //TODO: business logic to register an user into Proxime
        return response;
    }
}
