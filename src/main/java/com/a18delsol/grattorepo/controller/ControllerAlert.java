package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.alert.ModelAlert;
import com.a18delsol.grattorepo.service.ServiceAlert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/alert")
public class ControllerAlert {
    @Autowired
    private ServiceAlert service;

    @GetMapping(path = "/{alertID}")
    public @ResponseBody ResponseEntity<ModelAlert> alertGetOne(@PathVariable Integer alertID) {
        return service.alertGetOne(alertID);
    }

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelAlert>> alertGetAll() {
        return service.alertGetAll();
    }

    @GetMapping(path="/find")
    public @ResponseBody ResponseEntity<Iterable<ModelAlert>> alertFind(
            @RequestParam Optional<String> alertName) {
        return service.alertFind(alertName);
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelAlert>> alertCreate(@RequestBody Iterable<ModelAlert> alert) {
        return service.alertCreate(alert);
    }

    @DeleteMapping(path = "/{alertID}")
    public @ResponseBody ResponseEntity<String> alertDelete(@PathVariable Integer alertID) {
        return service.alertDelete(alertID);
    }

    @PatchMapping(path = "/{alertID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<String> alertPatch(@RequestBody JsonPatch patch, @PathVariable Integer alertID) throws JsonPatchException, JsonProcessingException {
        return service.alertPatch(patch, alertID);
    }

    //========================================================================

    @PostMapping(path = "/{alertID}/discard")
    public @ResponseBody ResponseEntity<ModelAlert> alertDiscard(@PathVariable Integer alertID) {
        return service.alertDiscard(alertID);
    }
}
