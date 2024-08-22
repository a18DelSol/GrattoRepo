package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.history.ModelHistory;
import com.a18delsol.grattorepo.service.ServiceHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping(path="/history")
public class ControllerHistory {
    @Autowired
    private ServiceHistory service;

    @GetMapping(path = "/{historyID}")
    public @ResponseBody ResponseEntity<ModelHistory> historyGetOne(@PathVariable Integer historyID) {
        return service.historyGetOne(historyID);
    }

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelHistory>> historyGetAll() {
        return service.historyGetAll();
    }

    @GetMapping(path="/find")
    public @ResponseBody ResponseEntity<Iterable<ModelHistory>> historyFind(
            @RequestParam Optional<LocalDate> historyDateMin,
            @RequestParam Optional<LocalDate> historyDateMax,
            @RequestParam Optional<LocalDate> historyTimeMin,
            @RequestParam Optional<LocalDate> historyTimeMax) {
        return service.historyFind(historyDateMin, historyDateMax, historyTimeMin, historyTimeMax);
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelHistory>> historyCreate(@RequestBody Iterable<ModelHistory> history) {
        return service.historyCreate(history);
    }

    @DeleteMapping(path = "/{historyID}")
    public @ResponseBody ResponseEntity<String> historyDelete(@PathVariable Integer historyID) {
        return service.historyDelete(historyID);
    }

    @PatchMapping(path = "/{historyID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<ModelHistory> historyPatch(@RequestBody JsonPatch patch, @PathVariable Integer historyID) throws JsonPatchException, JsonProcessingException {
        return service.historyPatch(patch, historyID);
    }
}
