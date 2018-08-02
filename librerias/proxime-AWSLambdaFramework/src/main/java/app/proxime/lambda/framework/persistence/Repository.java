package app.proxime.lambda.framework.persistence;

import java.util.List;

public interface Repository<DomainModel> {

    boolean insertOrUpdate(DomainModel model);

    DomainModel findByField(String field, String name);

    List<DomainModel> findAll();

    boolean delete(DomainModel model);
}
