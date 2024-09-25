package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.contact.ModelContact;
import com.a18delsol.grattorepo.service.ServiceContact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/contact")
public class ControllerContact {
    @Autowired
    private ServiceContact service;

    @GetMapping(path = "/{contactID}")
    public @ResponseBody ResponseEntity<ModelContact> contactGetOne(@PathVariable Integer contactID) {
        return service.contactGetOne(contactID);
    }

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelContact>> contactGetAll() {
        return service.contactGetAll();
    }

    @GetMapping(path="/find")
    public @ResponseBody ResponseEntity<Iterable<ModelContact>> contactFind(
            @RequestParam Optional<String> contactName,
            @RequestParam Optional<String> contactMail,
            @RequestParam Optional<String> contactCall) {
        return service.contactFind(contactName, contactMail, contactCall);
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<String> contactCreate(@Valid @RequestBody ModelContact contact) {
        return service.contactCreate(contact);
    }

    @DeleteMapping(path = "/{contactID}")
    public @ResponseBody ResponseEntity<String> contactDelete(@PathVariable Integer contactID) {
        return service.contactDelete(contactID);
    }

    @PatchMapping(path = "/{contactID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<String> contactPatch(@RequestBody JsonPatch patch, @PathVariable Integer contactID) throws JsonPatchException, JsonProcessingException {
        return service.contactPatch(patch, contactID);
    }
}