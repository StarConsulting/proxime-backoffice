package app.proxime.lambda.src.domain.services.login;

import app.proxime.lambda.framework.context.BaseRequest;

public class LoginRequest extends BaseRequest {

    public String username;
    public String password;
}
