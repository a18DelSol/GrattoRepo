package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.repository.RepositoryCategory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceCategory {
    public static ModelCategory categoryCreate(ModelCategory category, RepositoryCategory repository) {
        ModelCategory modelCategoryFind = repository.findByCategoryName(category.getCategoryName());

        if (modelCategoryFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        }

        repository.save(category);

        return category;
    }
}
