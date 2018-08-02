package app.proxime.lambda.framework.input;

import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.mocks.context.SimpleRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class InputTest {

    @Test
    public void tryToBuildInputFromValidInputStream() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("{\"is\":\"Test\"}".getBytes());
        String expected = "{\"is\":\"Test\"}";

        Input input = Input.fromInputStream(inputStream);

        assertEquals(expected, input.getInputString());
    }

    @Test
    public void tryToThrowLambdaExceptionForInvalidInputStream(){
        InputStream inputStream = new ByteArrayInputStream("invalid input".getBytes());

        LambdaException exceptionThrown = assertThrows(LambdaException.class,
                ()-> {
                    Input.fromInputStream(inputStream);
                }
                );
        assertEquals("{" +
                "\"id\":\"10\"," +
                "\"errorType\":\"Invalid input\"," +
                "\"message\":\"The input format is incorrect\"," +
                "\"errors\":[\"Must be a valid Json\"]" +
                "}", exceptionThrown.getMessage());
    }

    @Test
    public void tryToCreateAnInputFromString() {

        assertThat(Input.fromString("{"), instanceOf(Input.class));
    }

    @Test
    public void tryToParseInputTypeJsonToMap() {
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("test", "Okey");
        Input input = Input.fromString("{\"test\":\"Okey\"}");

        Map<String, Object> mappedInput = input.jsonToMap();
        assertEquals(expectedMap.get("test"), mappedInput.get("test"));
    }

    @Test
    public void tryToParseMapToInputTypeJson() {
        Input expectedInputJson = Input.fromString("{\"test\":\"Okey\"}");
        Map<String, Object> map = new HashMap();
        map.put("test", "Okey");
        assertEquals(expectedInputJson.getInputString(), Input.fromMap(map).getInputString());
    }

    @Test
    public void tryToParseJsonToPojo() {
        Input inputWithJson = Input.fromString("{\"test\":\"Okey\"}");
        SimpleRequest request = new SimpleRequest();
        assertThat(inputWithJson.pojoFromJson(request), instanceOf(SimpleRequest.class));
    }

}
