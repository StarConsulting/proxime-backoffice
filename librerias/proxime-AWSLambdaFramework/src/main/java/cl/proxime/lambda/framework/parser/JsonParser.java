package cl.proxime.lambda.framework.parser;

import cl.proxime.lambda.framework.context.BaseRequest;
import cl.proxime.lambda.framework.context.BaseResponse;

import java.util.Map;

public interface JsonParser {

    Map<String, Object> stringToMap(String json);

    String mapToString(Map<String, Object> mappedInput);

    BaseRequest fromJson(String inputString, BaseRequest request);

    String toJson(BaseResponse baseResponse);
}
