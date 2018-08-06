package app.proxime.lambda.framework.parser;

import app.proxime.lambda.framework.context.BaseRequest;
import app.proxime.lambda.framework.context.BaseResponse;

import java.util.Map;

public class Parser {

    private JsonParser jsonParser;

    public Parser(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public Map<String, Object> jsonToMap(String json) {
        return jsonParser.stringToMap(json);
    }

    public String mapToJson(Map<String, Object> mappedInput) {
        return jsonParser.mapToString(mappedInput);
    }

    public BaseRequest baseRequestFromJson(String inputString, BaseRequest request) {
        return jsonParser.fromJson(inputString, request);
    }

    public String baseResponseToJson(BaseResponse baseResponse) {
        return jsonParser.toJson(baseResponse);
    }
}
