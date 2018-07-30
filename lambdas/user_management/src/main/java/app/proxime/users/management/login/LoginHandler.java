package app.proxime.users.management.login;

import cl.proxime.lambda.framework.context.Lambda;
import cl.proxime.lambda.framework.exception.LambdaException;

public class LoginHandler implements Lambda<LoginRequest,LoginResponse> {
    @Override
    public LoginResponse execute(LoginRequest request) throws LambdaException {
        LoginResponse response=new LoginResponse();


        //TODO: business logic to authorize an user
        return null;
    }
}
