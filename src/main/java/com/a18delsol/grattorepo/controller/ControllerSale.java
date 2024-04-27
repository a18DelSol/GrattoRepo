package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelUser;
import com.a18delsol.grattorepo.repository.RepositorySale;
import com.a18delsol.grattorepo.service.ServiceSale;
import com.a18delsol.grattorepo.model.ModelSale;
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
@RequestMapping(path="/saleController")
public class ControllerSale {
    @Autowired
    private RepositorySale repositorySale;

    @GetMapping(path="/sale")
    public @ResponseBody Iterable<ModelSale> saleGet () {
        return repositorySale.findAll();
    }

    @GetMapping(path="/sale/{saleID}")
    public @ResponseBody ModelSale saleGet(@PathVariable Integer saleID) {
        ModelSale modelSaleFind = repositorySale.findById(saleID).orElse(null);

        if (modelSaleFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found.");
        }

        return modelSaleFind;
    }

    @GetMapping(path="/sale/find")
    public @ResponseBody Iterable<ModelSale> saleFind (@RequestParam Integer saleUserID) {
        return repositorySale.findSale(saleUserID);
    }

    @PatchMapping(path="/sale/{saleID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelSale salePatch(@PathVariable Integer saleID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelSale modelSaleFind = repositorySale.findById(saleID).orElse(null);

        if (modelSaleFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found.");
        }

        modelSaleFind = ServiceSale.salePatch(patch, modelSaleFind);

        repositorySale.save(modelSaleFind);

        return modelSaleFind;
    }

    @PostMapping(path="/sale")
    public @ResponseBody ArrayList<ModelSale> salePost (@RequestBody ArrayList<ModelSale> modelSaleData) {
        ArrayList<ModelSale> returnList = new ArrayList<>();

        for (ModelSale a : modelSaleData) {
            returnList.add(ServiceSale.saleCreate(a, repositorySale));
        }

        return returnList;
    }

    /* EXCLUSIVE */

    @PostMapping(path="/sale/purchase")
    public @ResponseBody ModelSale salePurchase (@RequestBody ModelSale modelSaleData) {
        return ServiceSale.salePurchase(modelSaleData, repositorySale);
    }
}
