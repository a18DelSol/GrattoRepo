package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelAttribute;
import com.a18delsol.grattorepo.model.ModelCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryCategory extends CrudRepository<ModelCategory, Integer> {
    ModelCategory findByCategoryName(String categoryName);

    @Query(nativeQuery=true, value="SELECT * FROM model_category WHERE category_name regexp :categoryName")
    Iterable<ModelCategory> findCategory(String categoryName);
}
