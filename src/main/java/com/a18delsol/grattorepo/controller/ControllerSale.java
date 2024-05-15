package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.repository.RepositorySale;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.a18delsol.grattorepo.service.ServiceSale;
import com.a18delsol.grattorepo.model.ModelSale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/sale")
public class ControllerSale {
    @Autowired private RepositorySale repositorySale;
    @Autowired private RepositoryUser repositoryUser;
    @Autowired private RepositoryItem repositoryItem;

    @GetMapping(path="/")
    public @ResponseBody Iterable<ModelSale> saleGet () {
        return repositorySale.findAll();
    }

    @GetMapping(path="/{saleID}")
    public @ResponseBody ModelSale saleGet(@PathVariable Integer saleID) {
        ModelSale modelSaleFind = repositorySale.findById(saleID).orElse(null);

        if (modelSaleFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found.");
        }

        return modelSaleFind;
    }

    @GetMapping(path="/find")
    public @ResponseBody Iterable<ModelSale> saleFind (@RequestParam Integer saleUserID) {
        return repositorySale.findSale(saleUserID);
    }

    @PatchMapping(path="/{saleID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelSale salePatch(@PathVariable Integer saleID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelSale modelSaleFind = repositorySale.findById(saleID).orElse(null);

        if (modelSaleFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found.");
        }

        modelSaleFind = ServiceSale.salePatch(patch, modelSaleFind);

        repositorySale.save(modelSaleFind);

        return modelSaleFind;
    }

    @PostMapping(path="/")
    public @ResponseBody ArrayList<ModelSale> salePost (@RequestBody ArrayList<ModelSale> modelSaleData) {
        ArrayList<ModelSale> returnList = new ArrayList<>();

        for (ModelSale a : modelSaleData) {
            returnList.add(ServiceSale.saleCreate(a, repositorySale));
        }

        return returnList;
    }

    /* EXCLUSIVE */

    @GetMapping(path="/checkOut/{userID}")
    public @ResponseBody ModelSale saleCheckOut (@PathVariable Integer userID) {
        return ServiceSale.saleCheckOut(userID, repositorySale, repositoryUser);
    }

    @PostMapping(path="/purchase/{userID}")
    public @ResponseBody ModelSale salePurchase (@PathVariable Integer userID, @RequestBody ModelSale sale) {
        return ServiceSale.salePurchase(userID, repositorySale, repositoryUser, repositoryItem, sale);
    }

    @PostMapping(path="/refund/{saleID}")
    public @ResponseBody ResponseEntity<String> salePurchase (@PathVariable Integer saleID) {
        return ServiceSale.saleRefund(saleID, repositorySale, repositoryUser, repositoryItem);
    }
}
