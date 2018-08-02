package app.proxime.lambda.framework.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(Intercepts.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercept {
    String classPath();
}
