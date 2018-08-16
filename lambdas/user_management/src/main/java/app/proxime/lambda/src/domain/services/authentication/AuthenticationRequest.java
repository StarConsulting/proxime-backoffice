package app.proxime.lambda.src.domain.services.authentication;

import app.proxime.lambda.framework.context.BaseRequest;

public class AuthenticationRequest extends BaseRequest {

    public String username;
    public String password;
}
