package cl.proxime.lambda.framework.input;

import cl.proxime.lambda.mocks.context.SimpleRequest;
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
    public void tryToCreateAnInputFromString() {

        assertThat(Input.fromString("{"), instanceOf(Input.class));
    }

    @Test
    public void tryToValidateAnValidJson() {
        Input input = Input.fromString("{");

        assertTrue(input.isValid());
    }

    @Test
    public void tryToValidateAnInvalidJson() {
        final String inputString = "invalid input";
        Input input = Input.fromString(inputString);

        assertFalse(input.isValid());
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
        assertEquals(expectedInputJson.getInputString(), Input.fromMap(map).jsonFromMap().getInputString());
    }

    @Test
    public void tryToParseJsonToPojo() {
        Input inputWithJson = Input.fromString("{\"test\":\"Okey\"}");
        SimpleRequest request = new SimpleRequest();
        assertThat(inputWithJson.pojoFromJson(request), instanceOf(SimpleRequest.class));
    }

    @Test
    public void tryToParseInputStreamToString() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("Test".getBytes());
        Input input = Input.fromInputStream(inputStream);
        assertEquals("Test", input.inputStreamToString());
    }
}
