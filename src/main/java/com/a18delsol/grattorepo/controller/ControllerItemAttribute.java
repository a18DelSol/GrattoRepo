package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelItemAttribute;
import com.a18delsol.grattorepo.repository.RepositoryItemAttribute;
import com.a18delsol.grattorepo.service.ServiceItemAttribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/itemAttribute")
public class ControllerItemAttribute {
    @Autowired
    private RepositoryItemAttribute repositoryItemAttribute;

    @GetMapping(path="/")
    public @ResponseBody Iterable<ModelItemAttribute> categoryGet () {
        return repositoryItemAttribute.findAll();
    }

    @GetMapping(path="/{attributeID}")
    public @ResponseBody ModelItemAttribute categoryGet(@PathVariable Integer categoryID) {
        ModelItemAttribute modelItemAttributeFind = repositoryItemAttribute.findById(categoryID).orElse(null);

        if (modelItemAttributeFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
        }

        return modelItemAttributeFind;
    }

    @GetMapping(path="/find")
    public @ResponseBody Iterable<ModelItemAttribute> categoryFind (@RequestParam String categoryName) {
        return repositoryItemAttribute.findAttribute(categoryName);
    }

    @PatchMapping(path="/{attributeID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelItemAttribute categoryPatch(@PathVariable Integer categoryID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelItemAttribute modelItemAttributeFind = repositoryItemAttribute.findById(categoryID).orElse(null);

        if (modelItemAttributeFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
        }

        modelItemAttributeFind = ServiceItemAttribute.categoryPatch(patch, modelItemAttributeFind);

        repositoryItemAttribute.save(modelItemAttributeFind);

        return modelItemAttributeFind;
    }

    @PostMapping(path="/")
    public @ResponseBody ArrayList<ModelItemAttribute> categoryPost (@RequestBody ArrayList<ModelItemAttribute> modelItemAttributeData) {
        ArrayList<ModelItemAttribute> returnList = new ArrayList<>();

        for (ModelItemAttribute a : modelItemAttributeData) {
            returnList.add(ServiceItemAttribute.categoryCreate(a, repositoryItemAttribute));
        }

        return returnList;
    }
}