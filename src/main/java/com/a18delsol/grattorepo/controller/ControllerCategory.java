package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.repository.RepositoryCategory;
import com.a18delsol.grattorepo.service.ServiceCategory;
import com.a18delsol.grattorepo.service.ServiceCategory;
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
@RequestMapping(path="/categoryController")
public class ControllerCategory {
    @Autowired
    private RepositoryCategory repositoryCategory;

    @GetMapping(path="/category")
    public @ResponseBody Iterable<com.a18delsol.grattorepo.model.ModelCategory> categoryGet () {
        return repositoryCategory.findAll();
    }

    @GetMapping(path="/category/{categoryID}")
    public @ResponseBody ModelCategory categoryGet(@PathVariable Integer categoryID) {
        ModelCategory modelCategoryFind = repositoryCategory.findById(categoryID).orElse(null);

        if (modelCategoryFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
        }

        return modelCategoryFind;
    }

    @GetMapping(path="/category/find")
    public @ResponseBody Iterable<ModelCategory> categoryFind (@RequestParam String categoryName) {
        return repositoryCategory.findCategory(categoryName);
    }

    @PatchMapping(path="/category/{categoryID}", consumes = "application/json-patch+json")
    public @ResponseBody com.a18delsol.grattorepo.model.ModelCategory categoryPatch(@PathVariable Integer categoryID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        com.a18delsol.grattorepo.model.ModelCategory modelCategoryFind = repositoryCategory.findById(categoryID).orElse(null);

        if (modelCategoryFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found.");
        }

        modelCategoryFind = ServiceCategory.categoryPatch(patch, modelCategoryFind);

        repositoryCategory.save(modelCategoryFind);

        return modelCategoryFind;
    }

    @PostMapping(path="/category")
    public @ResponseBody ArrayList<com.a18delsol.grattorepo.model.ModelCategory> categoryPost (@RequestBody ArrayList<com.a18delsol.grattorepo.model.ModelCategory> modelCategoryData) {
        ArrayList<com.a18delsol.grattorepo.model.ModelCategory> returnList = new ArrayList<>();

        for (ModelCategory a : modelCategoryData) {
            returnList.add(ServiceCategory.categoryCreate(a, repositoryCategory));
        }

        return returnList;
    }
}