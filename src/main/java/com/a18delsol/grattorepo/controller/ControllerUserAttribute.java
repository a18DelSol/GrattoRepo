package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelUserAttribute;
import com.a18delsol.grattorepo.repository.RepositoryUserAttribute;
import com.a18delsol.grattorepo.service.ServiceUserAttribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/userAttribute")
public class ControllerUserAttribute {
    @Autowired
    private RepositoryUserAttribute repositoryUserAttribute;

    @GetMapping(path="/")
    public @ResponseBody Iterable<ModelUserAttribute> attributeGet() {
        return repositoryUserAttribute.findAll();
    }

    @GetMapping(path="/{attributeID}")
    public @ResponseBody ModelUserAttribute attributeGet(@PathVariable Integer attributeID) {
        ModelUserAttribute modelUserAttributeFind = repositoryUserAttribute.findById(attributeID).orElse(null);

        if (modelUserAttributeFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found.");
        }

        return modelUserAttributeFind;
    }

    @GetMapping(path="/find")
    public @ResponseBody Iterable<ModelUserAttribute> attributeFind (@RequestParam String attributeName) {
        return repositoryUserAttribute.findAttribute(attributeName);
    }

    @PatchMapping(path="/{attributeID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelUserAttribute attributePatch(@PathVariable Integer attributeID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelUserAttribute modelUserAttributeFind = repositoryUserAttribute.findById(attributeID).orElse(null);

        if (modelUserAttributeFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found.");
        }

        modelUserAttributeFind = ServiceUserAttribute.attributePatch(patch, modelUserAttributeFind);

        repositoryUserAttribute.save(modelUserAttributeFind);

        return modelUserAttributeFind;
    }

    @DeleteMapping(path="/{attributeID}")
    public @ResponseBody ModelUserAttribute attributeDelete(@PathVariable Integer attributeID) {
        ModelUserAttribute modelUserAttributeFind = repositoryUserAttribute.findById(attributeID).orElse(null);

        if (modelUserAttributeFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found.");
        }

        repositoryUserAttribute.delete(modelUserAttributeFind);

        return modelUserAttributeFind;
    }

    @PostMapping(path="/")
    public @ResponseBody ArrayList<ModelUserAttribute> attributePost (@RequestBody ArrayList<ModelUserAttribute> modelUserAttributeData) {
        ArrayList<ModelUserAttribute> returnList = new ArrayList<>();

        for (ModelUserAttribute a : modelUserAttributeData) {
            returnList.add(ServiceUserAttribute.attributeCreate(a, repositoryUserAttribute));
        }

        return returnList;
    }
}
