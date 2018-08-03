package app.proxime.lambda.src.infrastructure.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.domain.user.UserRepository;
import app.proxime.lambda.src.infrastructure.dao.DynamoDBDAO;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class UserDynamoDBRepository implements UserRepository<UserDynamoDB> {

    private DynamoDBDAO<UserDynamoDB> dynamoDBDAO;

    public UserDynamoDBRepository(){
        this.dynamoDBDAO = new DynamoDBDAO<>(UserDynamoDB.class);
    }

    @Override
    public User getById(String id) {
        UserDynamoDB userDynamoDB = this.dynamoDBDAO.getById(id);

        return buildDomainModelFrom(userDynamoDB);
    }

    @Override
    public void insertOrUpdate(User user){
        dynamoDBDAO.insertOrUpdate(buildPersistenceModelFrom(user));
    }

    @Override
    public void insertOrUpdate(List<User> users) {
        dynamoDBDAO.insertOrUpdate(buildPersistenceModelFrom(users));
    }

    @Override
    public User getByField(String field, String value) {
        UserDynamoDB userDynamoDB = dynamoDBDAO.getByField(field,value);

        return buildDomainModelFrom(userDynamoDB);

    }

    @Override
    public User getByField(String field, int value) {
        return buildDomainModelFrom(dynamoDBDAO.getByField(field, value));
    }

    @Override
    public User getByField(String field, long value) {
        return buildDomainModelFrom(dynamoDBDAO.getByField(field, value));
    }

    @Override
    public List<User> getListByField(String field, String value) {
        return buildDomainModelFrom(dynamoDBDAO.getListByField(field, value));
    }

    @Override
    public void delete(User user) {
        dynamoDBDAO.delete(buildPersistenceModelFrom(user));
    }

    @Override
    public List<User> getListBySearchParameters(Map<String, AttributeValue> searchParameters, int limit) {
        return buildDomainModelFrom(dynamoDBDAO.getListBySearchParameters(searchParameters, limit));
    }

    @Override
    public List<User> getListBySearchParameters(Map<String, AttributeValue> searchParameters) {
        return buildDomainModelFrom(dynamoDBDAO.getListBySearchParameters(searchParameters));
    }

    @Override
    public List<User> query(String query, Map<String, AttributeValue> searchParameters, int limit) {
        return buildDomainModelFrom(dynamoDBDAO.query(query,searchParameters, limit));
    }

    @Override
    public List<User> query(String query, Map<String, AttributeValue> searchParameters) {
        return buildDomainModelFrom(dynamoDBDAO.query(query, searchParameters));
    }

    @Override
    public User buildDomainModelFrom(UserDynamoDB userDynamoDB) {
        return new User(
                userDynamoDB.getId(),
                userDynamoDB.getName(),
                userDynamoDB.getUsername(),
                userDynamoDB.getEmail(),
                userDynamoDB.getPassword(),
                userDynamoDB.getPhone(),
                userDynamoDB.getBirthdate()
        );
    }

    public List<User> buildDomainModelFrom(List<UserDynamoDB> usersDynamoDB) {
        List<User> users = new ArrayList<>();
        for (UserDynamoDB userDynamoDB:usersDynamoDB) {
            users.add(buildDomainModelFrom(userDynamoDB));
        }

        return users;
    }

    @Override
    public UserDynamoDB buildPersistenceModelFrom(User user) {
        UserDynamoDB userDynamoDB = new UserDynamoDB();
        userDynamoDB.setId(user.getId());
        userDynamoDB.setName(user.getName());
        userDynamoDB.setUsername(user.getUsername());
        userDynamoDB.setEmail(user.getEmail());
        userDynamoDB.setPhone(user.getPhone());
        userDynamoDB.setBirthdate(user.getBirthdate());

        return userDynamoDB;
    }

    public List<UserDynamoDB> buildPersistenceModelFrom(List<User> users) {

        List<UserDynamoDB> usersDynamoDB = new ArrayList<>();
        for (User user:users) {
            usersDynamoDB.add(buildPersistenceModelFrom(user));
        }

        return usersDynamoDB;
    }

}

