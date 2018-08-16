package app.proxime.lambda.src.domain.services.register;

import app.proxime.lambda.framework.context.Lambda;
import app.proxime.lambda.framework.context.ResponseInformation;
import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.domain.user.UserRepository;
import com.amazonaws.services.lambda.runtime.Context;

public class RegisterLambda implements Lambda<RegisterRequest, RegisterResponse> {

    private UserRepository repository;

    public RegisterLambda(UserRepository repository) {

        this.repository = repository;
    }

    public RegisterResponse execute(RegisterRequest request, Context context){

        User user = buildUserWith(request);

        ExternalRegisterResponse registerResponse = repository.signUp(user);

        if (registerResponse.hasError()) return registerError(registerResponse);

        return successfulRegister(registerResponse);

    }


    /*
    * Methods for responses building
    * */

    private RegisterResponse registerError(ExternalRegisterResponse registerResponse){
        RegisterResponse response = new RegisterResponse();
        ResponseInformation information = new ResponseInformation();

        information.id = registerResponse.getErrorId();
        information.message = registerResponse.getErrorMessage();
        information.errors = registerResponse.getErrorList();
        information.transactionId = registerResponse.getRequestId();

        response.info = information;

        return response;
    }

    private RegisterResponse successfulRegister(ExternalRegisterResponse registerResponse) {
        RegisterResponse response = new RegisterResponse();
        response.response = registerResponse.getSuccessMessage();
        return response;
    }




    private User buildUserWith(RegisterRequest request) {
        return new User(
                request.name,
                request.familyName,
                request.username,
                request.email,
                request.password,
                request.phone
        );
    }
}