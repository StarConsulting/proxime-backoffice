package cl.proxime.lambda.framework.context;

import cl.proxime.lambda.framework.exception.LambdaException;

public interface Lambda<Req extends BaseRequest, Res extends BaseResponse> {
    Res execute(Req request) throws LambdaException;
}
