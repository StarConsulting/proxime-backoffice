package app.proxime.lambda.src.infrastructure.cognito;

public class ClientCredentials {

    private String poolId = "us-east-2_79XWdam6i";
    private String clientId = "3shmpk7593s3llevfkek2cl7jh";

    public String getPoolId(){
        return this.poolId;
    }

    public String getClientId(){
        return this.clientId;
    }
}
