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

        LoginResponse response;

        User user = repository.findByField("username",request.username);
        if (user == null){
            response= new LoginResponse();
            response.greeting = "El username ingresado no está registrado";

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
