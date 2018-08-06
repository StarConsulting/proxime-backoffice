package app.proxime.lambda.framework.context;

import app.proxime.lambda.framework.exception.LambdaException;

public interface Lambda<Req extends BaseRequest, Res extends BaseResponse> {
    Res execute(Req request) throws LambdaException;
}
