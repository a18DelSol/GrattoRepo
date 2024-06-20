package com.a18delsol.grattorepo.repository.user;

import com.a18delsol.grattorepo.model.user.ModelUser;
import com.a18delsol.grattorepo.model.user.ModelUserAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryUserAttribute extends CrudRepository<ModelUserAttribute, Integer> {
    ModelUserAttribute findByAttributeName(String attributeName);

    @Query(nativeQuery=true,
    value="SELECT * FROM model_user_attribute"
    + " WHERE (:attributeName is null OR attribute_name regexp :attributeName)")
    Iterable<ModelUserAttribute> findUserAttribute(Optional<String> attributeName);
}
