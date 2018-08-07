package app.proxime.lambda;



public enum ResponseMessages {
    USERNAME_IS_ALREADY_IN_USE(1, "The username is already in use"),
    EMAIL_IS_ALREADY_IN_USE(2, "The email is already in use"),
    EMAIL_IS_NOT_REGISTERED(3, "The specified email is not registered"),
    PASSWORD_IS_INCORRECT(4, "The specified credentials are incorrect");

    private int code;
    private String message;

    private ResponseMessages(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
