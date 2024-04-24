package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelCategory;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryCategory extends CrudRepository<ModelCategory, Integer> {
    ModelCategory findByCategoryName(String categoryName);
}
