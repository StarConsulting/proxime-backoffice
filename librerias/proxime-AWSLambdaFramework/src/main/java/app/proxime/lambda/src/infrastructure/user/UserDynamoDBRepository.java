package app.proxime.lambda.src.infrastructure.user;

import app.proxime.lambda.src.domain.user.User;
import app.proxime.lambda.src.domain.user.UserRepository;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDynamoDBRepository implements UserRepository {

    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;

    public UserDynamoDBRepository(){
        this.client = AmazonDynamoDBClientBuilder.standard().build();
        this.mapper = new DynamoDBMapper(client);
    }



    public boolean insertOrUpdate(User user){

        UserDynamoDB userDynamoDB = new UserDynamoDB();
        userDynamoDB.setId(user.getId());
        userDynamoDB.setName(user.getName());
        userDynamoDB.setUsername(user.getUsername());
        userDynamoDB.setEmail(user.getEmail());
        userDynamoDB.setPassword(user.getPassword());
        userDynamoDB.setPhone(user.getPhone());
        userDynamoDB.setBirthdate(user.getBirthdate());

        try{
            mapper.save(userDynamoDB);
            return true;
        }catch (RuntimeException ex){
            return false;
        }
    }

    @Override
    public User findByField(String field, String value) {
        Map<String, AttributeValue> searchParameters = new HashMap<String, AttributeValue>();
        searchParameters.put(":" + field, new AttributeValue().withS(value));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(field + "=:" + field)
                .withLimit(1)
                .withExpressionAttributeValues(searchParameters);
        List<UserDynamoDB> scanResult = mapper.parallelScan(UserDynamoDB.class, scanExpression, 4);

        if (scanResult.isEmpty()){
            return null;
        }
        UserDynamoDB userDynamoDB = scanResult.get(0);

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
}

