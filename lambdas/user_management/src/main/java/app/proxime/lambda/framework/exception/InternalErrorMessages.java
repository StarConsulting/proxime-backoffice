package app.proxime.lambda.framework.exception;

public enum InternalErrorMessages {

    SERVICE_NOT_FOUND(100, "Service not found"),
    INVALID_INPUT(101, "The input has a invalid format, must be a valid JSON"),
    FILTER_NOT_FOUND(102, "The specified filter has not found"),

    ;


    private int code;
    private String message;

    InternalErrorMessages(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getId() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
