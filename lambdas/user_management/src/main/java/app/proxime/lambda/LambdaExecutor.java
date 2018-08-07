package app.proxime.lambda;

import app.proxime.lambda.framework.controller.FrontController;
import app.proxime.lambda.framework.core.FW;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LambdaExecutor implements RequestStreamHandler {
    private FrontController frontController = new FrontController();

    public LambdaExecutor() {
    }

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        FW fw = new FW();
        fw.run(inputStream, outputStream, context);
    }
}