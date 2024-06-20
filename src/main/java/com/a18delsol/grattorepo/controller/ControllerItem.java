package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.item.ModelItem;
import com.a18delsol.grattorepo.model.item.ModelItemAttribute;
import com.a18delsol.grattorepo.model.item.ModelItemCompany;
import com.a18delsol.grattorepo.service.ServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/item")
public class ControllerItem {
    @Autowired
    private ServiceItem service;

    @GetMapping(path = "/{itemID}")
    public @ResponseBody ResponseEntity<ModelItem> itemGetOne(@PathVariable Integer itemID) {
        return service.itemGetOne(itemID);
    }

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelItem>> itemGetAll() {
        return service.itemGetAll();
    }

    @GetMapping(path="/find")
    public @ResponseBody ResponseEntity<Iterable<ModelItem>> itemFind(
        @RequestParam Optional<String> itemName,
        @RequestParam Optional<String> itemCode) {
        return service.itemFind(itemName, itemCode);
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelItem>> itemCreate(@RequestBody Iterable<ModelItem> item) {
        return service.itemCreate(item);
    }

    @DeleteMapping(path = "/{itemID}")
    public @ResponseBody ResponseEntity<String> itemDelete(@PathVariable Integer itemID) {
        return service.itemDelete(itemID);
    }

    //========================================================================
    // ModelItemAttribute sub-controller
    //========================================================================

    @GetMapping(path = "/attribute/{itemAttributeID}")
    public @ResponseBody ResponseEntity<ModelItemAttribute> itemAttributeGetOne(@PathVariable Integer itemAttributeID) {
        return service.itemAttributeGetOne(itemAttributeID);
    }

    @GetMapping(path = "/attribute")
    public @ResponseBody ResponseEntity<Iterable<ModelItemAttribute>> itemAttributeGetAll() {
        return service.itemAttributeGetAll();
    }

    @GetMapping(path="/attribute/find")
    public @ResponseBody ResponseEntity<Iterable<ModelItemAttribute>> itemAttributeFind(
            @RequestParam Optional<String> itemAttributeName) {
        return service.itemAttributeFind(itemAttributeName);
    }

    @PostMapping(path = "/attribute")
    public @ResponseBody ResponseEntity<Iterable<ModelItemAttribute>> itemAttributeCreate(@RequestBody Iterable<ModelItemAttribute> itemAttribute) {
        return service.itemAttributeCreate(itemAttribute);
    }

    @DeleteMapping(path = "/attribute/{itemAttributeID}")
    public @ResponseBody ResponseEntity<String> itemAttributeDelete(@PathVariable Integer itemAttributeID) {
        return service.itemAttributeDelete(itemAttributeID);
    }

    //========================================================================
    // ModelItemCompany sub-controller
    //========================================================================

    @GetMapping(path = "/company/{itemCompanyID}")
    public @ResponseBody ResponseEntity<ModelItemCompany> itemCompanyGetOne(@PathVariable Integer itemCompanyID) {
        return service.itemCompanyGetOne(itemCompanyID);
    }

    @GetMapping(path = "/company")
    public @ResponseBody ResponseEntity<Iterable<ModelItemCompany>> itemCompanyGetAll() {
        return service.itemCompanyGetAll();
    }

    @GetMapping(path="/company/find")
    public @ResponseBody ResponseEntity<Iterable<ModelItemCompany>> itemCompanyFind(
            @RequestParam Optional<String> itemCompanyName) {
        return service.itemCompanyFind(itemCompanyName);
    }

    @PostMapping(path = "/company")
    public @ResponseBody ResponseEntity<Iterable<ModelItemCompany>> itemCompanyCreate(@RequestBody Iterable<ModelItemCompany> itemCompany) {
        return service.itemCompanyCreate(itemCompany);
    }

    @DeleteMapping(path = "/company/{itemCompanyID}")
    public @ResponseBody ResponseEntity<String> itemCompanyDelete(@PathVariable Integer itemCompanyID) {
        return service.itemCompanyDelete(itemCompanyID);
    }
}
