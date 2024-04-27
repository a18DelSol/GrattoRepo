package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelAttribute;
import com.a18delsol.grattorepo.repository.RepositoryAttribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceAttribute {
    public static ModelAttribute attributeCreate(ModelAttribute attribute, RepositoryAttribute repository) {
        ModelAttribute modelAttributeFind = repository.findByAttributeName(attribute.getAttributeName());

        if (modelAttributeFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        }

        repository.save(attribute);

        return attribute;
    }

    public static ModelAttribute attributePatch(JsonPatch patch, ModelAttribute attribute) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(attribute, JsonNode.class)), ModelAttribute.class);
    }
}
