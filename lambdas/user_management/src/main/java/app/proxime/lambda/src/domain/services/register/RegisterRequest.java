package app.proxime.lambda.src.domain.services.register;

import app.proxime.lambda.framework.context.BaseRequest;

public class RegisterRequest extends BaseRequest {

    public String name;

    public String familyName;

    public String username;

    public String email;

    public String password;

    public String phone;

}