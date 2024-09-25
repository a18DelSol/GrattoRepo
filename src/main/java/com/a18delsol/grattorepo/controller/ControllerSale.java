package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.sale.ModelSale;
import com.a18delsol.grattorepo.model.sale.ModelSaleOrder;
import com.a18delsol.grattorepo.service.ServiceSale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping(path="/sale")
public class ControllerSale {
    @Autowired
    private ServiceSale service;

    @GetMapping(path = "/{saleID}")
    public @ResponseBody ResponseEntity<ModelSale> saleGetOne(@PathVariable Integer saleID) {
        return service.saleGetOne(saleID);
    }

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelSale>> saleGetAll() {
        return service.saleGetAll();
    }

    @GetMapping(path="/find")
    public @ResponseBody ResponseEntity<Iterable<ModelSale>> saleFind(
            @RequestParam Optional<Float> salePriceMin,
            @RequestParam Optional<Float> salePriceMax,
            @RequestParam Optional<LocalDate> saleDateMin,
            @RequestParam Optional<LocalDate> saleDateMax) {
        return service.saleFind(salePriceMin, salePriceMax, saleDateMin, saleDateMax);
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelSale>> saleCreate(@RequestBody Iterable<ModelSale> sale) {
        return service.saleCreate(sale);
    }

    @DeleteMapping(path = "/{saleID}")
    public @ResponseBody ResponseEntity<String> saleDelete(@PathVariable Integer saleID) {
        return service.saleDelete(saleID);
    }

    @PatchMapping(path = "/{saleID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<String> salePatch(@RequestBody JsonPatch patch, @PathVariable Integer saleID) throws JsonPatchException, JsonProcessingException {
        return service.salePatch(patch, saleID);
    }

    //========================================================================

    @GetMapping(path="/report")
    public @ResponseBody ResponseEntity<String> saleReport(
            @RequestParam Optional<LocalDate> saleDateMin,
            @RequestParam Optional<LocalDate> saleDateMax,
            @RequestParam Optional<String>    salePath) {
        return service.saleReport(saleDateMin, saleDateMax, salePath);
    }

    @PostMapping(path = "/buy")
    public @ResponseBody ResponseEntity<ModelSale> saleBuy(@RequestBody @Valid ModelSale sale) {
        return service.saleBuy(sale);
    }

    @PostMapping(path = "/{saleID}/return")
    public @ResponseBody ResponseEntity<String> saleReturn(@PathVariable Integer saleID) {
        return service.saleReturn(saleID);
    }

    //========================================================================
    // ModelSaleOrder sub-controller
    //========================================================================

    @GetMapping(path = "/order/{saleOrderID}")
    public @ResponseBody ResponseEntity<ModelSaleOrder> saleOrderGetOne(@PathVariable Integer saleOrderID) {
        return service.saleOrderGetOne(saleOrderID);
    }

    @GetMapping(path = "/order")
    public @ResponseBody ResponseEntity<Iterable<ModelSaleOrder>> saleOrderGetAll() {
        return service.saleOrderGetAll();
    }

    @GetMapping(path="/order/find")
    public @ResponseBody ResponseEntity<Iterable<ModelSaleOrder>> saleOrderFind(
            @RequestParam Optional<Float> orderAmountMin,
            @RequestParam Optional<Float> orderAmountMax) {
        return service.saleOrderFind(orderAmountMin, orderAmountMax);
    }

    @PostMapping(path = "/order")
    public @ResponseBody ResponseEntity<Iterable<ModelSaleOrder>> saleOrderCreate(@RequestBody Iterable<ModelSaleOrder> saleOrder) {
        return service.saleOrderCreate(saleOrder);
    }

    @DeleteMapping(path = "/order/{saleOrderID}")
    public @ResponseBody ResponseEntity<String> saleOrderDelete(@PathVariable Integer saleOrderID) {
        return service.saleOrderDelete(saleOrderID);
    }

    @PatchMapping(path = "/order/{saleOrderID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<String> saleOrderPatch(@RequestBody JsonPatch patch, @PathVariable Integer saleOrderID)
            throws JsonPatchException, JsonProcessingException {
        return service.saleOrderPatch(patch, saleOrderID);
    }
}
