package cl.proxime.lambda.framework.input;

import cl.proxime.lambda.framework.context.BaseRequest;
import cl.proxime.lambda.framework.parser.GsonParser;
import cl.proxime.lambda.framework.parser.Parser;
import com.amazonaws.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class Input {

    private Map<String, Object> mappedInput;
    private String inputString;
    private Parser parser;
    private InputStream inputStream;

    private Input(String inputString) {

        this.inputString = inputString;
        this.mappedInput = new LinkedHashMap<>();
        this.parser = new Parser(new GsonParser());
        this.inputStream = new ByteArrayInputStream("".getBytes());
    }

    private Input(Map<String, Object> mappedInput) {
        this.inputString = "";
        this.mappedInput = mappedInput;
        this.parser = new Parser(new GsonParser());
        this.inputStream = new ByteArrayInputStream("".getBytes());
    }

    private Input(InputStream inputStream){
        this.inputString = "";
        this.mappedInput = new LinkedHashMap<>();
        this.parser = new Parser(new GsonParser());
        this.inputStream = inputStream;
    }

    public static Input fromString(String inputString) {
        return new Input(inputString);
    }

    public static Input fromMap(Map<String, Object> mappedInput) {
        return new Input(mappedInput);
    }

    public static Input fromInputStream(InputStream inputStream) {
        return new Input(inputStream);
    }

    public Input jsonFromMap() {
        return Input.fromString(parser.mapToJson(this.mappedInput));
    }

    public String getInputString() {
        return this.inputString;
    }

    public boolean isValid() {

        if (inputString.startsWith("{")) {
            return true;
        }
        return false;
    }

    public Map<String, Object> jsonToMap() {
        return parser.jsonToMap(this.inputString);
    }

    public BaseRequest pojoFromJson(BaseRequest request) {
        return parser.baseRequestFromJson(this.inputString, request);
    }

    public String inputStreamToString() throws IOException {
        return IOUtils.toString(this.inputStream);
    }
}
