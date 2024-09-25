package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.contact.ModelContact;
import com.a18delsol.grattorepo.model.item.ModelItem;
import com.a18delsol.grattorepo.model.item.ModelItemAttribute;
import com.a18delsol.grattorepo.model.item.ModelItemCompany;
import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import com.a18delsol.grattorepo.repository.contact.RepositoryContact;
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
    @Autowired RepositoryContact       repositoryContact;
    @Autowired ServiceHistory          serviceHistory;
    @Autowired ServiceStock            serviceStock;

    public ResponseEntity<ModelItem> itemGetOne(Integer itemID) {
        return new ResponseEntity<>(repositoryItem.findByIDAndEntityDeleteFalse(itemID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItem>> itemGetAll() {
        return new ResponseEntity<>(repositoryItem.findByEntityDeleteFalse(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItem>> itemFind(
        Optional<String> itemName,
        Optional<String> itemCode,
        Optional<Integer> itemCountMin,
        Optional<Integer> itemCountMax) {
        return new ResponseEntity<>(repositoryItem.findItem(itemName, itemCode, itemCountMin, itemCountMax), HttpStatus.OK);
    }

    public ResponseEntity<String> itemCreate(ModelItem item) {
        repositoryItem.save(item);
        serviceHistory.historyCreate(String.format("[Producto] Creación (%s [%s])",
                item.getItemName(),
                item.getItemCode()
        ));

        return new ResponseEntity<>("Creation OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> itemDelete(Integer itemID) {
        ModelItem item = repositoryItem.findById(itemID).orElseThrow(RuntimeException::new);

        for (ModelContact i : item.getItemContact()) {
            i.getContactItem().remove(item);
            repositoryContact.save(i);
        }

        for (ModelStockEntry i : item.getItemEntry()) {
            serviceStock.stockEntryDelete(i.getID());
        }

        item.setEntityDelete(true);

        serviceHistory.historyCreate(String.format("[Producto] Eliminación (%s [%s])",
                item.getItemName(),
                item.getItemCode()
        ));

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> itemPatch(JsonPatch patch, Integer itemID) throws JsonPatchException, JsonProcessingException {
        ModelItem item = repositoryItem.findById(itemID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        repositoryItem.save(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(item, JsonNode.class)), ModelItem.class));

        return new ResponseEntity<>("Patch OK.", HttpStatus.OK);
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
                newCount
        ));

        return new ResponseEntity<>("Update OK.", HttpStatus.OK);
    }

    //========================================================================
    // ModelItemAttribute sub-service
    //========================================================================

    public ResponseEntity<ModelItemAttribute> itemAttributeGetOne(Integer itemAttributeID) {
        return new ResponseEntity<>(repositoryItemAttribute.findByIDAndEntityDeleteFalse(itemAttributeID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemAttribute>> itemAttributeGetAll() {
        return new ResponseEntity<>(repositoryItemAttribute.findByEntityDeleteFalse(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemAttribute>> itemAttributeFind(
            Optional<String> itemAttributeName) {
        return new ResponseEntity<>(repositoryItemAttribute.findItemAttribute(itemAttributeName), HttpStatus.OK);
    }

    public ResponseEntity<String> itemAttributeCreate(ModelItemAttribute itemAttribute) {
        repositoryItemAttribute.save(itemAttribute);

        return new ResponseEntity<>("Creation OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> itemAttributeDelete(Integer itemAttributeID) {
        ModelItemAttribute itemAttribute = repositoryItemAttribute.findById(itemAttributeID).orElseThrow(RuntimeException::new);

        itemAttribute.setEntityDelete(true);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> itemAttributePatch(JsonPatch patch, Integer itemAttributeID) throws JsonPatchException, JsonProcessingException {
        ModelItemAttribute itemAttribute = repositoryItemAttribute.findById(itemAttributeID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        repositoryItemAttribute.save(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(itemAttribute, JsonNode.class)), ModelItemAttribute.class));

        return new ResponseEntity<>("Patch OK.", HttpStatus.OK);
    }

    //========================================================================
    // ModelItemCompany sub-service
    //========================================================================

    public ResponseEntity<ModelItemCompany> itemCompanyGetOne(Integer itemCompanyID) {
        return new ResponseEntity<>(repositoryItemCompany.findByIDAndEntityDeleteFalse(itemCompanyID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemCompany>> itemCompanyGetAll() {
        return new ResponseEntity<>(repositoryItemCompany.findByEntityDeleteFalse(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelItemCompany>> itemCompanyFind(
            Optional<String> itemCompanyName) {
        return new ResponseEntity<>(repositoryItemCompany.findItemCompany(itemCompanyName), HttpStatus.OK);
    }

    public ResponseEntity<String> itemCompanyCreate(ModelItemCompany itemCompany) {
        repositoryItemCompany.save(itemCompany);

        return new ResponseEntity<>("Creation OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> itemCompanyDelete(Integer itemCompanyID) {
        ModelItemCompany itemCompany = repositoryItemCompany.findById(itemCompanyID).orElseThrow(RuntimeException::new);

        for (ModelContact i : itemCompany.getCompanyContact()) {
            i.getContactCompany().remove(itemCompany);
            repositoryContact.save(i);
        }

        itemCompany.setEntityDelete(true);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> itemCompanyPatch(JsonPatch patch, Integer itemCompanyID) throws JsonPatchException, JsonProcessingException {
        ModelItemCompany itemCompany = repositoryItemCompany.findById(itemCompanyID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        repositoryItemCompany.save(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(itemCompany, JsonNode.class)), ModelItemCompany.class));

        return new ResponseEntity<>("Patch OK.", HttpStatus.OK);
    }
}
