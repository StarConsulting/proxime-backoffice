package app.proxime.lambda.src.domain.services.register;

import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.exception.LambdaException;
import app.proxime.lambda.src.domain.user.UserRepository;

import java.util.UUID;

public class RegisterLambda
        implements Lambda<RegisterRequest, RegisterResponse> {

    private UserRepository repository;

    public RegisterLambda(
            UserRepository repository
    ){
        this.repository = repository;
    }

    @Override
    public RegisterResponse execute(RegisterRequest request)
            throws LambdaException {
        RegisterResponse response = new RegisterResponse();

        User user = new User(
                UUID.randomUUID().toString(),
                request.name,
                request.username,
                request.email,
                request.password,
                request.phone,
                request.birthdate
        );


        User userFound = repository.findByField("username", user.getUsername());
        if (userFound != null){
            response.response = "El username especificado ya se encuentra registrado";
            return response;
        }
        User userWithEmail = repository.findByField("email",user.getEmail());
        if (userWithEmail != null){
            response.response = "El correo especificado ya se encuentra registrado";
            return response;
        }

        repository.insertOrUpdate(user);


        response.response = "Successful registration";

        return response;
    }
}
