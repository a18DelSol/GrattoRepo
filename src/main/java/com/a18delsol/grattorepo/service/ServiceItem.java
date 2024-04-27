package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelItem;
import com.a18delsol.grattorepo.model.ModelItem;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceItem {
    public static ModelItem itemCreate(ModelItem item, RepositoryItem repository) {
        ModelItem modelItemFind = repository.findByItemName(item.getItemName());

        if (modelItemFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        }

        repository.save(item);

        return item;
    }

    public static ModelItem itemPatch(JsonPatch patch, ModelItem item) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(item, JsonNode.class)), ModelItem.class);
    }
}
