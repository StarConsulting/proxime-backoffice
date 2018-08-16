package app.proxime.lambda.src.domain.services.authentication;

import java.util.List;

public interface ExternalAuthenticationResponse {

    String getAccessToken();
    long getTokenDuration();

    int getErrorId();
    String getErrorMessage();
    List<String> getErrorList();

    String getRequestId();

    boolean hasError();
}
