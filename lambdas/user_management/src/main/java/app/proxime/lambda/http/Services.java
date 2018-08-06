package app.proxime.lambda.http;

import app.proxime.lambda.framework.service.ServicesMapper;
import app.proxime.lambda.src.application.services.login.LoginService;
import app.proxime.lambda.src.application.services.register.RegisterService;

public class Services extends ServicesMapper {

    public Services(){

        declare("register", new RegisterService());
        declare("login", new LoginService());

    }
}
