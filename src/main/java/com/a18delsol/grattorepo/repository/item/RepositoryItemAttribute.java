package com.a18delsol.grattorepo.repository.item;

import com.a18delsol.grattorepo.model.item.ModelItemAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryItemAttribute extends CrudRepository<ModelItemAttribute, Integer> {
    ModelItemAttribute findByAttributeName(String attributeName);
    Optional<ModelItemAttribute> findByIDAndEntityDeleteFalse(Integer ID);
    Iterable<ModelItemAttribute> findByEntityDeleteFalse();

    @Query(nativeQuery=true,
    value="SELECT * FROM model_item_attribute"
    + " WHERE (entity_delete is false)"
    + " AND (:attributeName is null OR attribute_name regexp :attributeName)")
    Iterable<ModelItemAttribute> findItemAttribute(Optional<String> attributeName);
}
