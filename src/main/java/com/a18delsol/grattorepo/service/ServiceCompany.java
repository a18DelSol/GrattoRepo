package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelCompany;
import com.a18delsol.grattorepo.model.ModelCompany;
import com.a18delsol.grattorepo.repository.RepositoryCompany;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceCompany {
    public static ModelCompany companyCreate(ModelCompany company, RepositoryCompany repository) {
        ModelCompany modelCompanyFind = repository.findByCompanyName(company.getCompanyName());

        if (modelCompanyFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        }

        repository.save(company);

        return company;
    }

    public static ModelCompany companyPatch(JsonPatch patch, ModelCompany company) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(company, JsonNode.class)), ModelCompany.class);
    }
}
