package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelAttribute;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryAttribute extends CrudRepository<ModelAttribute, Integer> {
    ModelAttribute findByAttributeName(String attributeName);
}
