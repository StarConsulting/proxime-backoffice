package app.proxime.lambda.src.infrastructure.cognito;

public class CognitoClientCredentials {

    private String poolId = "us-east-2_ooraCYfEq";
    private String clientId = "3nvgod1v5msmf4lfnc6tkodd64";

    public String getPoolId(){
        return this.poolId;
    }

    public String getClientId(){
        return this.clientId;
    }
}
