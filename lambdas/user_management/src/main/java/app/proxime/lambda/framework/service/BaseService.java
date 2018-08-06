package app.proxime.lambda.framework.service;

import app.proxime.lambda.framework.input.Input;
import app.proxime.lambda.framework.interceptor.Interceptor;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseService implements Service {

    private Interceptor interceptor;

    public BaseService(){
        this.interceptor = new Interceptor();
    }

    @Override
    public abstract void execute(Input input, OutputStream outputStream, Context context) throws IOException;


}
