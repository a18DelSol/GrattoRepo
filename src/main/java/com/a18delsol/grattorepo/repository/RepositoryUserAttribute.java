package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelUserAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryUserAttribute extends CrudRepository<ModelUserAttribute, Integer> {
    ModelUserAttribute findByAttributeName(String attributeName);

    @Query(nativeQuery=true, value="SELECT * FROM model_user_attribute WHERE attribute_name regexp :attributeName")
    Iterable<ModelUserAttribute> findAttribute(String attributeName);
}
