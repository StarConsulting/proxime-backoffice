package cl.proxime.lambda.framework.context;

import java.util.Date;
import java.util.List;

public class ResponseInformation {
    public int code;
    public String message;
    public List<String> errors;
    public Date date;
    public long duration;
    public String transactionId;

}
