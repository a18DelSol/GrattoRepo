package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryAttribute extends CrudRepository<ModelAttribute, Integer> {
    ModelAttribute findByAttributeName(String attributeName);

    @Query(nativeQuery=true, value="SELECT * FROM model_attribute WHERE attribute_name regexp :attributeName")
    Iterable<ModelAttribute> findAttribute(String attributeName);
}
