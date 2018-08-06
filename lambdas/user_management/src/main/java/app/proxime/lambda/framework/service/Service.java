package app.proxime.lambda.framework.service;

import app.proxime.lambda.framework.input.Input;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.OutputStream;

public interface Service {

    void execute(
            Input input,
            OutputStream outputStream,
            Context context
    ) throws IOException;
}
