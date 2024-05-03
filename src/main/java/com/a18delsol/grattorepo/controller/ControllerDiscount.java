package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelDiscount;
import com.a18delsol.grattorepo.model.ModelDiscount;
import com.a18delsol.grattorepo.model.ModelDiscount;
import com.a18delsol.grattorepo.repository.RepositoryDiscount;
import com.a18delsol.grattorepo.service.ServiceDiscount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(path="/discount")
public class ControllerDiscount {
    @Autowired
    private RepositoryDiscount repositoryDiscount;

    @GetMapping(path="/")
    public @ResponseBody Iterable<com.a18delsol.grattorepo.model.ModelDiscount> discountGet () {
        return repositoryDiscount.findAll();
    }

    @GetMapping(path="/{discountID}")
    public @ResponseBody ModelDiscount discountGet(@PathVariable Integer discountID) {
        ModelDiscount modelDiscountFind = repositoryDiscount.findById(discountID).orElse(null);

        if (modelDiscountFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount not found.");
        }

        return modelDiscountFind;
    }

    @GetMapping(path="/find")
    public @ResponseBody Iterable<ModelDiscount> discountFind (@RequestParam Optional<String> discountName,
        @RequestParam Optional<Float> discountPercentMin, @RequestParam Optional<Float> discountPercentMax) {
        return repositoryDiscount.findDiscount(discountName, discountPercentMin, discountPercentMax);
    }

    @PatchMapping(path="/{discountID}", consumes = "application/json-patch+json")
    public @ResponseBody com.a18delsol.grattorepo.model.ModelDiscount discountPatch(@PathVariable Integer discountID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        com.a18delsol.grattorepo.model.ModelDiscount modelDiscountFind = repositoryDiscount.findById(discountID).orElse(null);

        if (modelDiscountFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount not found.");
        }

        modelDiscountFind = ServiceDiscount.discountPatch(patch, modelDiscountFind);

        repositoryDiscount.save(modelDiscountFind);

        return modelDiscountFind;
    }

    @PostMapping(path="/")
    public @ResponseBody ArrayList<com.a18delsol.grattorepo.model.ModelDiscount> discountPost (@RequestBody ArrayList<com.a18delsol.grattorepo.model.ModelDiscount> modelDiscountData) {
        ArrayList<com.a18delsol.grattorepo.model.ModelDiscount> returnList = new ArrayList<>();

        for (ModelDiscount a : modelDiscountData) {
            returnList.add(ServiceDiscount.discountCreate(a, repositoryDiscount));
        }

        return returnList;
    }
}
