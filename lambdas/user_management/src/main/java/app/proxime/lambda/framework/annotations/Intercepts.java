package app.proxime.lambda.framework.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Intercepts {
    Intercept[] value();
}
