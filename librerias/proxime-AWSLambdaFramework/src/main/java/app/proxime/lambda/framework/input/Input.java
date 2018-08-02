package app.proxime.lambda.framework.input;

import app.proxime.lambda.framework.context.BaseRequest;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.exception.LambdaExceptionHandler;
import app.proxime.lambda.framework.parser.GsonParser;
import app.proxime.lambda.framework.parser.Parser;
import com.amazonaws.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Input {

    private String inputString;

    private LambdaExceptionHandler lambdaExceptionHandler;
    private Parser parser;

    private Input(InputStream inputStream) throws IOException {
        this.inputString = IOUtils.toString(inputStream);
        this.parser = new Parser(new GsonParser());
    }

    private Input(Map<String, Object> mappedInput) {
        this.lambdaExceptionHandler = new LambdaExceptionHandler();
        this.parser = new Parser(new GsonParser());
        this.inputString = parser.mapToJson(mappedInput);
    }

    private Input(String inputString){
        this.lambdaExceptionHandler = new LambdaExceptionHandler();
        this.parser = new Parser(new GsonParser());
        this.inputString = inputString;
    }

    public static Input fromString(String inputString){
        return new Input(inputString);
    }

    public static Input fromMap(Map<String, Object> mappedInput) {
        return new Input(mappedInput);
    }

    public static Input fromInputStream(InputStream inputStream) throws IOException {
        Input input = new Input(inputStream);
        return input;
}

    private LambdaException buildInvalidInputLambdaException() {
        lambdaExceptionHandler.setId("10");
        lambdaExceptionHandler.setErrorType("Invalid input");
        lambdaExceptionHandler.setMessage("The input format is incorrect");
        lambdaExceptionHandler.addError("Must be a valid Json");

        return lambdaExceptionHandler.throwException();
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        return IOUtils.toString(inputStream);
    }

    public String getInputString() {
        return this.inputString;
    }

    private boolean isValid() {
        try{
            parser.jsonToMap(this.inputString);
        }catch (Exception ex){
            return false;
        }

        return true;
    }

    public Map<String, Object> jsonToMap() {
        return parser.jsonToMap(this.inputString);
    }

    public BaseRequest pojoFromJson(BaseRequest request) {
        return parser.baseRequestFromJson(this.inputString, request);
    }
}
