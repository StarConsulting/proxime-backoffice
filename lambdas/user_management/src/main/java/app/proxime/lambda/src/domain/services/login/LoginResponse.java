package app.proxime.lambda.src.domain.services.login;

import app.proxime.lambda.framework.context.BaseResponse;

public class LoginResponse extends BaseResponse {

    public String accessToken;
    public int tokenDuration;
}
