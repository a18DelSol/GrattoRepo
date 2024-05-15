package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.service.ServiceItem;
import com.a18delsol.grattorepo.model.ModelItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(path="/item")
public class ControllerItem {
    @Autowired
    private RepositoryItem repositoryItem;

    @GetMapping(path="/")
    public @ResponseBody Iterable<ModelItem> itemGet () {
        return repositoryItem.findAll();
    }

    @GetMapping(path="/{itemID}")
    public @ResponseBody ModelItem itemGet(@PathVariable Integer itemID) {
        ModelItem modelItemFind = repositoryItem.findById(itemID).orElse(null);

        if (modelItemFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
        }

        return modelItemFind;
    }

    @GetMapping(path="/find")
    public @ResponseBody Iterable<ModelItem> itemFind (
    @RequestParam Optional<String> itemName,
    @RequestParam Optional<String> itemCode,
    @RequestParam Optional<Float> itemPriceMin,
    @RequestParam Optional<Float> itemPriceMax,
    @RequestParam Optional<Integer> itemCountMin,
    @RequestParam Optional<Integer> itemCountMax,
    @RequestParam Optional<Boolean> itemRestrict) {
        return repositoryItem.findItem(itemName, itemCode, itemPriceMin, itemPriceMax, itemCountMin, itemCountMax, itemRestrict);
    }

    @PatchMapping(path="/{itemID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelItem itemPatch(@PathVariable Integer itemID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelItem modelItemFind = repositoryItem.findById(itemID).orElse(null);

        if (modelItemFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found.");
        }

        modelItemFind = ServiceItem.itemPatch(patch, modelItemFind);

        repositoryItem.save(modelItemFind);

        return modelItemFind;
    }

    @PostMapping(path="/")
    public @ResponseBody ArrayList<ModelItem> itemPost (@RequestBody ArrayList<ModelItem> modelItemData) {
        ArrayList<ModelItem> returnList = new ArrayList<>();

        for (ModelItem a : modelItemData) {
            returnList.add(ServiceItem.itemCreate(a, repositoryItem));
        }

        return returnList;
    }
}
