package app.proxime.lambda.src.domain.user;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.List;
import java.util.Map;

public interface UserRepository <PersistenceModel>{

    User getById(String id);

    void insertOrUpdate(User model);

    void insertOrUpdate(List<User> models);

    User getByField(String field, String value);

    User getByField(String field, int value);

    User getByField(String field, long value);

    List<User> getListByField(String field, String value);

    void delete(User model);

    List<User> getListBySearchParameters(Map<String, AttributeValue> searchParameters, int limit);

    List<User> getListBySearchParameters(Map<String, AttributeValue> searchParameters);

    List<User> query(String query, Map<String, AttributeValue> searchParameters, int limit);

    List<User> query(String query, Map<String, AttributeValue> searchParameters);

    User buildDomainModelFrom(PersistenceModel persistenceModel);

    List<User> buildDomainModelFrom(List<PersistenceModel> persistenceModels);

    PersistenceModel buildPersistenceModelFrom(User domainModel);

    List<PersistenceModel> buildPersistenceModelFrom(List<User> domainModels);
}
