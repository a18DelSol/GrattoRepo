package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelAttribute;
import com.a18delsol.grattorepo.repository.RepositoryAttribute;
import com.a18delsol.grattorepo.service.ServiceAttribute;
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
@RequestMapping(path="/attributeController")
public class ControllerAttribute {
    @Autowired
    private RepositoryAttribute repositoryAttribute;

    @GetMapping(path="/attribute")
    public @ResponseBody Iterable<ModelAttribute> attributeGet() {
        return repositoryAttribute.findAll();
    }

    @GetMapping(path="/attribute/{attributeID}")
    public @ResponseBody ModelAttribute attributeGet(@PathVariable Integer attributeID) {
        ModelAttribute modelAttributeFind = repositoryAttribute.findById(attributeID).orElse(null);

        if (modelAttributeFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found.");
        }

        return modelAttributeFind;
    }

    @GetMapping(path="/attribute/find")
    public @ResponseBody Iterable<ModelAttribute> attributeFind (@RequestParam String attributeName) {
        return repositoryAttribute.findAttribute(attributeName);
    }

    @PatchMapping(path="/attribute/{attributeID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelAttribute attributePatch(@PathVariable Integer attributeID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelAttribute modelAttributeFind = repositoryAttribute.findById(attributeID).orElse(null);

        if (modelAttributeFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found.");
        }

        modelAttributeFind = ServiceAttribute.attributePatch(patch, modelAttributeFind);

        repositoryAttribute.save(modelAttributeFind);

        return modelAttributeFind;
    }

    @DeleteMapping(path="/attribute/{attributeID}")
    public @ResponseBody ModelAttribute attributeDelete(@PathVariable Integer attributeID) {
        ModelAttribute modelAttributeFind = repositoryAttribute.findById(attributeID).orElse(null);

        if (modelAttributeFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found.");
        }

        repositoryAttribute.delete(modelAttributeFind);

        return modelAttributeFind;
    }

    @PostMapping(path="/attribute")
    public @ResponseBody ArrayList<ModelAttribute> attributePost (@RequestBody ArrayList<ModelAttribute> modelAttributeData) {
        ArrayList<ModelAttribute> returnList = new ArrayList<>();

        for (ModelAttribute a : modelAttributeData) {
            returnList.add(ServiceAttribute.attributeCreate(a, repositoryAttribute));
        }

        return returnList;
    }
}
