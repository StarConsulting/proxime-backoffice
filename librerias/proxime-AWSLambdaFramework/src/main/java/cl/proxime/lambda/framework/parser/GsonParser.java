package cl.proxime.lambda.framework.parser;

import cl.proxime.lambda.framework.context.BaseRequest;
import cl.proxime.lambda.framework.context.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class GsonParser implements JsonParser {

    private Gson gson;

    public GsonParser() {
        this.gson = new Gson();
    }

    @Override
    public Map<String, Object> stringToMap(String json) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public String mapToString(Map<String, Object> mappedInput) {
        return gson.toJson(mappedInput);
    }

    @Override
    public BaseRequest fromJson(String inputString, BaseRequest request) {
        return gson.fromJson(inputString, request.getClass());
    }

    @Override
    public String toJson(BaseResponse baseResponse) {
        return gson.toJson(baseResponse);
    }
}
