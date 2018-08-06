package app.proxime.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import app.proxime.lambda.framework.core.FW;
import com.amazonaws.services.lambda.runtime.Context;
import app.proxime.lambda.framework.controller.FrontController;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;


public class LambdaExecutor implements RequestStreamHandler {

    private FrontController frontController;
    public LambdaExecutor(){
        this.frontController = new FrontController();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        FW fw = new FW();
        fw.run(inputStream, outputStream,context);
    }
}
