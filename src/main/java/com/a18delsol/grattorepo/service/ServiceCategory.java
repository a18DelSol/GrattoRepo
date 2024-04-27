package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.repository.RepositoryCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

    public static ModelCategory categoryPatch(JsonPatch patch, ModelCategory category) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(category, JsonNode.class)), ModelCategory.class);
    }
}
