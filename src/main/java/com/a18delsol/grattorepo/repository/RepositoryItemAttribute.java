package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelItemAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryItemAttribute extends CrudRepository<ModelItemAttribute, Integer> {
    ModelItemAttribute findByAttributeName(String attributeName);

    @Query(nativeQuery=true, value="SELECT * FROM model_item_attribute WHERE attribute_name regexp :attributeName")
    Iterable<ModelItemAttribute> findAttribute(String attributeName);
}
