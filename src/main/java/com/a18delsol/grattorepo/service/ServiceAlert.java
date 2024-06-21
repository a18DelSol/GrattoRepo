package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.alert.ModelAlert;
import com.a18delsol.grattorepo.repository.alert.RepositoryAlert;
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
public class ServiceAlert {
    @Autowired
    RepositoryAlert repositoryAlert;

    public ResponseEntity<ModelAlert> alertGetOne(Integer alertID) {
        return new ResponseEntity<>(repositoryAlert.findById(alertID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelAlert>> alertGetAll() {
        return new ResponseEntity<>(repositoryAlert.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelAlert>> alertFind(
            Optional<String> alertName) {
        return new ResponseEntity<>(repositoryAlert.findAlert(alertName), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelAlert>> alertCreate(Iterable<ModelAlert> alert) {
        for (ModelAlert a : alert) {
            repositoryAlert.save(a);
        }

        return new ResponseEntity<>(repositoryAlert.findAll(), HttpStatus.OK);
    }

    public void alertCreate(ModelAlert alert) {
        repositoryAlert.save(alert);
    }

    public ResponseEntity<String> alertDelete(Integer alertID) {
        ModelAlert alert = repositoryAlert.findById(alertID).orElseThrow(RuntimeException::new);

        repositoryAlert.delete(alert);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelAlert> alertPatch(JsonPatch patch, Integer alertID) throws JsonPatchException, JsonProcessingException {
        ModelAlert alert = repositoryAlert.findById(alertID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(alert, JsonNode.class)), ModelAlert.class), HttpStatus.OK);
    }

    //========================================================================

    public ResponseEntity<ModelAlert> alertDiscard(Integer alertID) {
        ModelAlert alert = repositoryAlert.findById(alertID).orElseThrow(RuntimeException::new);

        alert.setAlertSeen(true);
        repositoryAlert.save(alert);

        return new ResponseEntity<>(alert, HttpStatus.OK);
    }
}
