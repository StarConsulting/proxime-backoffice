package app.proxime.lambda.src.domain.services.authentication;

import app.proxime.lambda.framework.context.BaseResponse;

public class AuthenticationResponse extends BaseResponse {

    public String accessToken;
    public long tokenDuration;
}
