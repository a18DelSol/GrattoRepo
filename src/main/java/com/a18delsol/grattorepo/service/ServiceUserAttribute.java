package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelUserAttribute;
import com.a18delsol.grattorepo.repository.RepositoryUserAttribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceUserAttribute {
    public static ModelUserAttribute attributeCreate(ModelUserAttribute attribute, RepositoryUserAttribute repository) {
        ModelUserAttribute modelUserAttributeFind = repository.findByAttributeName(attribute.getAttributeName());

        if (modelUserAttributeFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        }

        repository.save(attribute);

        return attribute;
    }

    public static ModelUserAttribute attributePatch(JsonPatch patch, ModelUserAttribute attribute) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(attribute, JsonNode.class)), ModelUserAttribute.class);
    }
}
