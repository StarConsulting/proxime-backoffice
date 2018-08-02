package app.proxime.lambda.src.domain.user;

public interface UserRepository {

    boolean insertOrUpdate(User user);
    User findByField(String field, String value);
}
