package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelAttribute;
import com.a18delsol.grattorepo.repository.RepositoryAttribute;
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
}
