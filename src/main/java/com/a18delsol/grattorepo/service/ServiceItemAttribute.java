package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelItemAttribute;
import com.a18delsol.grattorepo.repository.RepositoryItemAttribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceItemAttribute {
    public static ModelItemAttribute categoryCreate(ModelItemAttribute category, RepositoryItemAttribute repository) {
        ModelItemAttribute modelItemAttributeFind = repository.findByAttributeName(category.getAttributeName());

        if (modelItemAttributeFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        }

        repository.save(category);

        return category;
    }

    public static ModelItemAttribute categoryPatch(JsonPatch patch, ModelItemAttribute category) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(category, JsonNode.class)), ModelItemAttribute.class);
    }
}
