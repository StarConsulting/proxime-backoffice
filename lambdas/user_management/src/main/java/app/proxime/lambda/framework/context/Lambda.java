package app.proxime.lambda.framework.context;

import app.proxime.lambda.framework.exception.LambdaException;
import com.amazonaws.services.lambda.runtime.Context;

public interface Lambda<Req extends BaseRequest, Res extends BaseResponse> {
    Res execute(Req request, Context context) throws LambdaException;
}
