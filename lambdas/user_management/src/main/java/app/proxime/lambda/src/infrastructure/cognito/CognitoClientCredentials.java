package app.proxime.lambda.src.infrastructure.cognito;

public class CognitoClientCredentials {

    private String poolId = "us-east-2_ooraCYfEq";
    private String clientId = "75l3n8pk78qu1f08r1c2g4e77b";

    public String getPoolId(){
        return this.poolId;
    }

    public String getClientId(){
        return this.clientId;
    }
}
