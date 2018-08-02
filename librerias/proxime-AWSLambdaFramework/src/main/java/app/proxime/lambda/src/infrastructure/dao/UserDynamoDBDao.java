package app.proxime.lambda.src.infrastructure.dao;

import app.proxime.lambda.src.infrastructure.dao.core.DynamoDao;
import app.proxime.lambda.src.infrastructure.user.UserDynamoDB;

public class UserDynamoDBDao extends DynamoDao<UserDynamoDB> {
    public UserDynamoDBDao() {
        super(UserDynamoDB.class);
    }
}
