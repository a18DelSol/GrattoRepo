package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.discount.ModelDiscount;
import com.a18delsol.grattorepo.service.ServiceDiscount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/discount")
public class ControllerDiscount {
    @Autowired
    private ServiceDiscount service;

    @GetMapping(path = "/{discountID}")
    public @ResponseBody ResponseEntity<ModelDiscount> discountGetOne(@PathVariable Integer discountID) {
        return service.discountGetOne(discountID);
    }

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelDiscount>> discountGetAll() {
        return service.discountGetAll();
    }

    @GetMapping(path="/find")
    public @ResponseBody ResponseEntity<Iterable<ModelDiscount>> discountFind(
            @RequestParam Optional<String> discountName) {
        return service.discountFind(discountName);
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelDiscount>> discountCreate(@RequestBody Iterable<ModelDiscount> discount) {
        return service.discountCreate(discount);
    }

    @DeleteMapping(path = "/{discountID}")
    public @ResponseBody ResponseEntity<String> discountDelete(@PathVariable Integer discountID) {
        return service.discountDelete(discountID);
    }

    @PatchMapping(path = "/{discountID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<ModelDiscount> discountPatch(@RequestBody JsonPatch patch, @PathVariable Integer discountID) throws JsonPatchException, JsonProcessingException {
        return service.discountPatch(patch, discountID);
    }
}
