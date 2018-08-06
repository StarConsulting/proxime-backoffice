package app.proxime.lambda.src.domain.services.login;

import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.infrastructure.user.UserDynamoDBRepository;

public class LoginLambda implements Lambda<LoginRequest, LoginResponse> {

    private UserDynamoDBRepository repository;

    public LoginLambda(
            UserDynamoDBRepository repository
    ){
        this.repository = repository;
    }

    @Override
    public LoginResponse execute(LoginRequest request) throws LambdaException {

        LoginResponse response  = new LoginResponse();
        ResponseInformation responseInformation = new ResponseInformation();
        List<String> errors = new ArrayList<>();
        User user = repository.getByField("email",request.email);

        //revisar ¿Porque durationToken = 0?
        if (user == null){
            errors.add("The email "+ request.email + " is not registered");
            response.info = buildResponseInformation(
                    4,
                    "Failed authentication",
                    errors,
                    1000,
                    context.getAwsRequestId()
            );
            return response;
        }

        if (!user.getPassword().equals(request.password)){
            response= new LoginResponse();
            response.greeting = "La contraseña ingresada es erronea";

            return response;
        }

        response = new LoginResponse();
        response.greeting = "Bienvenido "+user.getName();
        return response;
    }
}
