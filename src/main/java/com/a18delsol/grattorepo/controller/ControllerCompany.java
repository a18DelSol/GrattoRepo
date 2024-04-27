package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelCompany;
import com.a18delsol.grattorepo.model.ModelCompany;
import com.a18delsol.grattorepo.model.ModelCompany;
import com.a18delsol.grattorepo.repository.RepositoryCompany;
import com.a18delsol.grattorepo.service.ServiceCompany;
import com.a18delsol.grattorepo.service.ServiceCompany;
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
@RequestMapping(path="/companyController")
public class ControllerCompany {
    @Autowired
    private RepositoryCompany repositoryCompany;

    @GetMapping(path="/company")
    public @ResponseBody Iterable<com.a18delsol.grattorepo.model.ModelCompany> companyGet () {
        return repositoryCompany.findAll();
    }

    @GetMapping(path="/company/{companyID}")
    public @ResponseBody ModelCompany companyGet(@PathVariable Integer companyID) {
        ModelCompany modelCompanyFind = repositoryCompany.findById(companyID).orElse(null);

        if (modelCompanyFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found.");
        }

        return modelCompanyFind;
    }

    @GetMapping(path="/company/find")
    public @ResponseBody Iterable<ModelCompany> companyFind (@RequestParam String companyName) {
        return repositoryCompany.findCompany(companyName);
    }

    @PatchMapping(path="/company/{companyID}", consumes = "application/json-patch+json")
    public @ResponseBody com.a18delsol.grattorepo.model.ModelCompany companyPatch(@PathVariable Integer companyID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        com.a18delsol.grattorepo.model.ModelCompany modelCompanyFind = repositoryCompany.findById(companyID).orElse(null);

        if (modelCompanyFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found.");
        }

        modelCompanyFind = ServiceCompany.companyPatch(patch, modelCompanyFind);

        repositoryCompany.save(modelCompanyFind);

        return modelCompanyFind;
    }

    @PostMapping(path="/company")
    public @ResponseBody ArrayList<com.a18delsol.grattorepo.model.ModelCompany> companyPost (@RequestBody ArrayList<com.a18delsol.grattorepo.model.ModelCompany> modelCompanyData) {
        ArrayList<com.a18delsol.grattorepo.model.ModelCompany> returnList = new ArrayList<>();

        for (ModelCompany a : modelCompanyData) {
            returnList.add(ServiceCompany.companyCreate(a, repositoryCompany));
        }

        return returnList;
    }
}
