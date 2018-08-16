package app.proxime.lambda.framework.core;

import app.proxime.lambda.framework.context.ResponseInformation;
import app.proxime.lambda.framework.controller.FrontController;
import app.proxime.lambda.framework.exception.ErrorFWResponse;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.framework.parser.GsonParser;
import app.proxime.lambda.framework.parser.Parser;
import app.proxime.lambda.framework.service.Service;
import app.proxime.lambda.framework.time.Timer;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;

public class FW {

    private FrontController frontController;
    private Parser parser;

    public FW(){
        this.frontController = new FrontController();
        this.parser = new Parser(new GsonParser());
    }

    public void run(
            InputStream inputStream,
            OutputStream outputStream,
            Context context
    ) throws IOException {

        Timer timer = Timer.getTimer();
        timer.start();

        Input input = Input.fromInputStream(inputStream);

        try{

            Service service = frontController.findService(input);
            service.execute(input, outputStream, context);

        }catch(LambdaException ex){

            ErrorFWResponse response = new ErrorFWResponse();
            ResponseInformation information = new ResponseInformation();

            information.id = ex.getErrorId();
            information.message = ex.getErrorMessage();
            information.errors = ex.getErrors();
            information.duration = getDurationInMilis();
            information.transactionId = context.getAwsRequestId();

            response.info = information;

            outputStream.write(parser.baseResponseToJson(response).getBytes());
        }

    }

    private long getDurationInMilis(){
        Timer timer = Timer.getTimer();
        timer.finish();
        return timer.getDuration();
    }
}
