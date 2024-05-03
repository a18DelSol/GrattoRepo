package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelItemCompany;
import com.a18delsol.grattorepo.repository.RepositoryItemCompany;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceItemCompany {
    public static ModelItemCompany companyCreate(ModelItemCompany company, RepositoryItemCompany repository) {
        ModelItemCompany modelItemCompanyFind = repository.findByCompanyName(company.getCompanyName());

        if (modelItemCompanyFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        }

        repository.save(company);

        return company;
    }

    public static ModelItemCompany companyPatch(JsonPatch patch, ModelItemCompany company) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(company, JsonNode.class)), ModelItemCompany.class);
    }
}
