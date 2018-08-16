package app.proxime.lambda.framework.context;

import java.util.Date;
import java.util.List;

public class ResponseInformation {
    public int id;
    public String message;
    public List<String> errors;
    public Date date = new Date();
    public String transactionId;
}