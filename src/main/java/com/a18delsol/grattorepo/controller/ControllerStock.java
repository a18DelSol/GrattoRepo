package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.stock.ModelStock;
import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import com.a18delsol.grattorepo.service.ServiceStock;
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
@RequestMapping(path="/stock")
public class ControllerStock {
    @Autowired
    private ServiceStock service;

    @GetMapping(path = "/{stockID}")
    public @ResponseBody ResponseEntity<ModelStock> stockGetOne(@PathVariable Integer stockID) {
        return service.stockGetOne(stockID);
    }

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelStock>> stockGetAll() {
        return service.stockGetAll();
    }

    @GetMapping(path="/find")
    public @ResponseBody ResponseEntity<Iterable<ModelStock>> stockFind(
            @RequestParam Optional<String> stockName) {
        return service.stockFind(stockName);
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<String> stockCreate(@Valid @RequestBody ModelStock stock) {
        return service.stockCreate(stock);
    }

    @DeleteMapping(path = "/{stockID}")
    public @ResponseBody ResponseEntity<String> stockDelete(@PathVariable Integer stockID) {
        return service.stockDelete(stockID);
    }

    @PatchMapping(path = "/{stockID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<ModelStock> stockPatch(@RequestBody JsonPatch patch, @PathVariable Integer stockID) throws JsonPatchException, JsonProcessingException {
        return service.stockPatch(patch, stockID);
    }

    //========================================================================
    // ModelStockEntry sub-controller
    //========================================================================

    @GetMapping(path = "/entry/{stockEntryID}")
    public @ResponseBody ResponseEntity<ModelStockEntry> stockEntryGetOne(@PathVariable Integer stockEntryID) {
        return service.stockEntryGetOne(stockEntryID);
    }

    @GetMapping(path = "/entry")
    public @ResponseBody ResponseEntity<Iterable<ModelStockEntry>> stockEntryGetAll() {
        return service.stockEntryGetAll();
    }

    @GetMapping(path="/entry/find")
    public @ResponseBody ResponseEntity<Iterable<ModelStockEntry>> stockEntryFind(
            @RequestParam Optional<Integer> entryCountMin,
            @RequestParam Optional<Integer> entryCountMax,
            @RequestParam Optional<Float> entryPriceMin,
            @RequestParam Optional<Float> entryPriceMax) {
        return service.stockEntryFind(entryCountMin, entryCountMax, entryPriceMin, entryPriceMax);
    }

    @PostMapping(path = "/entry")
    public @ResponseBody ResponseEntity<String> stockEntryCreate(@Valid @RequestBody ModelStockEntry stockEntry) {
        return service.stockEntryCreate(stockEntry);
    }

    @DeleteMapping(path = "/entry/{stockEntryID}")
    public @ResponseBody ResponseEntity<String> stockEntryDelete(@PathVariable Integer stockEntryID) {
        return service.stockEntryDelete(stockEntryID);
    }

    @PatchMapping(path = "/entry/{stockEntryID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<ModelStockEntry> stockEntryPatch(@RequestBody JsonPatch patch, @PathVariable Integer stockEntryID)
            throws JsonPatchException, JsonProcessingException {
        return service.stockEntryPatch(patch, stockEntryID);
    }

    //========================================================================

    @PatchMapping(path = "/entry/{stockEntryID}/count")
    public @ResponseBody ResponseEntity<String> stockEntryUpdateCount(@PathVariable Integer stockEntryID, @RequestParam Integer entryCount) {
        return service.stockEntryUpdateCount(stockEntryID, entryCount);
    }

    @PatchMapping(path = "/entry/{stockEntryID}/price")
    public @ResponseBody ResponseEntity<String> stockEntryUpdatePrice(@PathVariable Integer stockEntryID, @RequestParam Float entryPrice) {
        return service.stockEntryUpdatePrice(stockEntryID, entryPrice);
    }

    @GetMapping(path="/entry/report")
    public @ResponseBody ResponseEntity<String> stockEntryReport() {
        return service.stockEntryReport();
    }
}
