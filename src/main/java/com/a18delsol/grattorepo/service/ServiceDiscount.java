package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.discount.ModelDiscount;
import com.a18delsol.grattorepo.repository.discount.RepositoryDiscount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceDiscount {
    @Autowired
    RepositoryDiscount repositoryDiscount;

    public ResponseEntity<ModelDiscount> discountGetOne(Integer discountID) {
        return new ResponseEntity<>(repositoryDiscount.findById(discountID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelDiscount>> discountGetAll() {
        return new ResponseEntity<>(repositoryDiscount.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelDiscount>> discountFind(
            Optional<String> discountName) {
        return new ResponseEntity<>(repositoryDiscount.findDiscount(discountName), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelDiscount>> discountCreate(Iterable<ModelDiscount> discount) {
        for (ModelDiscount a : discount) {
            repositoryDiscount.save(a);
        }

        return new ResponseEntity<>(repositoryDiscount.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> discountDelete(Integer discountID) {
        ModelDiscount discount = repositoryDiscount.findById(discountID).orElseThrow(RuntimeException::new);

        repositoryDiscount.delete(discount);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelDiscount> discountPatch(JsonPatch patch, Integer discountID) throws JsonPatchException, JsonProcessingException {
        ModelDiscount discount = repositoryDiscount.findById(discountID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(discount, JsonNode.class)), ModelDiscount.class), HttpStatus.OK);
    }
}
