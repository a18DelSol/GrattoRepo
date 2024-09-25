package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.exception.EntityNotFound;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ServiceAlert {
    @Autowired
    RepositoryAlert repositoryAlert;

    public ResponseEntity<ModelAlert> alertGetOne(Integer alertID) {
        return new ResponseEntity<>(repositoryAlert.findById(alertID).orElseThrow(EntityNotFound::new), HttpStatus.OK);
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

    public void alertCreate(String alertText) {
        ModelAlert newAlert = new ModelAlert();
        newAlert.setAlertText(alertText);
        newAlert.setAlertDate(LocalDate.now());
        newAlert.setAlertTime(LocalTime.now());

        repositoryAlert.save(newAlert);
    }

    public ResponseEntity<String> alertDelete(Integer alertID) {
        ModelAlert alert = repositoryAlert.findById(alertID).orElseThrow(EntityNotFound::new);

        repositoryAlert.delete(alert);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> alertPatch(JsonPatch patch, Integer alertID) throws JsonPatchException, JsonProcessingException {
        ModelAlert alert = repositoryAlert.findById(alertID).orElseThrow(EntityNotFound::new);

        ObjectMapper objectMapper = new ObjectMapper();

        repositoryAlert.save(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(alert, JsonNode.class)), ModelAlert.class));

        return new ResponseEntity<>("Patch OK.", HttpStatus.OK);
    }

    //========================================================================

    public ResponseEntity<ModelAlert> alertDiscard(Integer alertID) {
        ModelAlert alert = repositoryAlert.findById(alertID).orElseThrow(EntityNotFound::new);

        repositoryAlert.delete(alert);

        return new ResponseEntity<>(alert, HttpStatus.OK);
    }
}
