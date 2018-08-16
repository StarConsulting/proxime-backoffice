package app.proxime.lambda.src.domain.services.register;

import java.util.List;

public interface ExternalRegisterResponse {

    String getSuccessMessage();

    int getErrorId();
    String getErrorMessage();
    List<String> getErrorList();

    String getRequestId();

    boolean hasError();
}
