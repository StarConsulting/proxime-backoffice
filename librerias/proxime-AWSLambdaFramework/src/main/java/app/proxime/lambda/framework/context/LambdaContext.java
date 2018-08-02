package app.proxime.lambda.framework.context;

import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.parser.GsonParser;
import app.proxime.lambda.framework.parser.Parser;

public class LambdaContext {

    private Lambda lambda;
    private BaseRequest request;
    private BaseResponse response;

    private Parser parser;

    public LambdaContext(Lambda lambda, BaseRequest request, BaseResponse response) {
        this.lambda = lambda;
        this.request = request;
        this.response = response;
        this.parser = new Parser(new GsonParser());
    }

    public String execute(String input) throws LambdaException {
        BaseRequest request = parser.baseRequestFromJson(input, this.request);
        BaseResponse response = this.lambda.execute(request);
        return parser.baseResponseToJson(response);
    }

    public Lambda getLambda() {
        return this.lambda;
    }
}
