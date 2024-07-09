package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.item.ModelItem;
import com.a18delsol.grattorepo.model.item.ModelItemAttribute;
import com.a18delsol.grattorepo.model.item.ModelItemCompany;
import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import com.a18delsol.grattorepo.repository.item.RepositoryItem;
import com.a18delsol.grattorepo.repository.item.RepositoryItemAttribute;
import com.a18delsol.grattorepo.repository.item.RepositoryItemCompany;
import com.a18delsol.grattorepo.repository.stock.RepositoryStockEntry;
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
public class ServiceItem {
    @Autowired RepositoryItem          repositoryItem;
    @Autowired RepositoryItemAttribute repositoryItemAttribute;
    @Autowired RepositoryItemCompany   repositoryItemCompany;
    @Autowired RepositoryStockEntry    repositoryStockEntry;
    @Autowired ServiceHistory          serviceHistory;

    public ResponseEntity<ModelItem> itemGetOne(Integer itemID) {
        return new ResponseEntity<>(repositoryItem.findById(itemID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItem>> itemGetAll() {
        return new ResponseEntity<>(repositoryItem.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItem>> itemFind(
        Optional<String> itemName,
        Optional<String> itemCode) {
        return new ResponseEntity<>(repositoryItem.findItem(itemName, itemCode), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItem>> itemCreate(Iterable<ModelItem> item) {
        for (ModelItem a : item) {
            repositoryItem.save(a);
            serviceHistory.historyCreate(String.format("[Producto] Creación (%s [%s])",
                    a.getItemName(),
                    a.getItemCode()));
        }

        return new ResponseEntity<>(repositoryItem.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> itemDelete(Integer itemID) {
        ModelItem item = repositoryItem.findById(itemID).orElseThrow(RuntimeException::new);

        repositoryItem.delete(item);
        serviceHistory.historyCreate(String.format("[Producto] Eliminación (%s [%s])",
                item.getItemName(),
                item.getItemCode()));

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelItem> itemPatch(JsonPatch patch, Integer itemID) throws JsonPatchException, JsonProcessingException {
        ModelItem item = repositoryItem.findById(itemID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(item, JsonNode.class)), ModelItem.class), HttpStatus.OK);
    }

    //========================================================================

    public ResponseEntity<String> itemUpdateCount(Integer itemID, Integer itemCount) {
        ModelItem item = repositoryItem.findById(itemID).orElseThrow(RuntimeException::new);
        Integer oldCount = item.getItemCount();
        Integer newCount = oldCount + itemCount;

        if (newCount < 0) {
            newCount = 0;
        }

        for (ModelStockEntry a : item.getItemEntry()) {
            if (a.getEntryCount() > newCount) {
                a.setEntryCount(newCount);
                repositoryStockEntry.save(a);
            }
        }

        item.setItemCount(newCount);
        repositoryItem.save(item);
        serviceHistory.historyCreate(String.format("[Producto] Actualización (%s [%s], stock anterior: %d, stock actual: %d)",
                item.getItemName(),
                item.getItemCode(),
                oldCount,
                newCount));

        return new ResponseEntity<>("Update OK.", HttpStatus.OK);
    }

    //========================================================================
    // ModelItemAttribute sub-service
    //========================================================================

    public ResponseEntity<ModelItemAttribute> itemAttributeGetOne(Integer itemAttributeID) {
        return new ResponseEntity<>(repositoryItemAttribute.findById(itemAttributeID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemAttribute>> itemAttributeGetAll() {
        return new ResponseEntity<>(repositoryItemAttribute.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemAttribute>> itemAttributeFind(
            Optional<String> itemAttributeName) {
        return new ResponseEntity<>(repositoryItemAttribute.findItemAttribute(itemAttributeName), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemAttribute>> itemAttributeCreate(Iterable<ModelItemAttribute> itemAttribute) {
        for (ModelItemAttribute a : itemAttribute) {
            repositoryItemAttribute.save(a);
        }

        return new ResponseEntity<>(repositoryItemAttribute.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> itemAttributeDelete(Integer itemAttributeID) {
        ModelItemAttribute itemAttribute = repositoryItemAttribute.findById(itemAttributeID).orElseThrow(RuntimeException::new);

        repositoryItemAttribute.delete(itemAttribute);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelItemAttribute> itemAttributePatch(JsonPatch patch, Integer itemAttributeID) throws JsonPatchException, JsonProcessingException {
        ModelItemAttribute itemAttribute = repositoryItemAttribute.findById(itemAttributeID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(itemAttribute, JsonNode.class)), ModelItemAttribute.class), HttpStatus.OK);
    }

    //========================================================================
    // ModelItemCompany sub-service
    //========================================================================

    public ResponseEntity<ModelItemCompany> itemCompanyGetOne(Integer itemCompanyID) {
        return new ResponseEntity<>(repositoryItemCompany.findById(itemCompanyID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemCompany>> itemCompanyGetAll() {
        return new ResponseEntity<>(repositoryItemCompany.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemCompany>> itemCompanyFind(
            Optional<String> itemCompanyName) {
        return new ResponseEntity<>(repositoryItemCompany.findItemCompany(itemCompanyName), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemCompany>> itemCompanyCreate(Iterable<ModelItemCompany> itemCompany) {
        for (ModelItemCompany a : itemCompany) {
            repositoryItemCompany.save(a);
        }

        return new ResponseEntity<>(repositoryItemCompany.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> itemCompanyDelete(Integer itemCompanyID) {
        ModelItemCompany itemCompany = repositoryItemCompany.findById(itemCompanyID).orElseThrow(RuntimeException::new);

        repositoryItemCompany.delete(itemCompany);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelItemCompany> itemCompanyPatch(JsonPatch patch, Integer itemCompanyID) throws JsonPatchException, JsonProcessingException {
        ModelItemCompany itemCompany = repositoryItemCompany.findById(itemCompanyID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(itemCompany, JsonNode.class)), ModelItemCompany.class), HttpStatus.OK);
    }
}
