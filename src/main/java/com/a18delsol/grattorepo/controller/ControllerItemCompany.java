package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelItemCompany;
import com.a18delsol.grattorepo.repository.RepositoryItemCompany;
import com.a18delsol.grattorepo.service.ServiceItemCompany;
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
@RequestMapping(path="/itemCompany")
public class ControllerItemCompany {
    @Autowired
    private RepositoryItemCompany repositoryItemCompany;

    @GetMapping(path="/")
    public @ResponseBody Iterable<ModelItemCompany> companyGet () {
        return repositoryItemCompany.findAll();
    }

    @GetMapping(path="/{companyID}")
    public @ResponseBody ModelItemCompany companyGet(@PathVariable Integer companyID) {
        ModelItemCompany modelItemCompanyFind = repositoryItemCompany.findById(companyID).orElse(null);

        if (modelItemCompanyFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found.");
        }

        return modelItemCompanyFind;
    }

    @GetMapping(path="/find")
    public @ResponseBody Iterable<ModelItemCompany> companyFind (@RequestParam String companyName) {
        return repositoryItemCompany.findCompany(companyName);
    }

    @PatchMapping(path="/{companyID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelItemCompany companyPatch(@PathVariable Integer companyID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelItemCompany modelItemCompanyFind = repositoryItemCompany.findById(companyID).orElse(null);

        if (modelItemCompanyFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found.");
        }

        modelItemCompanyFind = ServiceItemCompany.companyPatch(patch, modelItemCompanyFind);

        repositoryItemCompany.save(modelItemCompanyFind);

        return modelItemCompanyFind;
    }

    @PostMapping(path="/")
    public @ResponseBody ArrayList<ModelItemCompany> companyPost (@RequestBody ArrayList<ModelItemCompany> modelItemCompanyData) {
        ArrayList<ModelItemCompany> returnList = new ArrayList<>();

        for (ModelItemCompany a : modelItemCompanyData) {
            returnList.add(ServiceItemCompany.companyCreate(a, repositoryItemCompany));
        }

        return returnList;
    }
}
