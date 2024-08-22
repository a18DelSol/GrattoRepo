package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.history.ModelHistory;
import com.a18delsol.grattorepo.repository.history.RepositoryHistory;
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
public class ServiceHistory {
    @Autowired
    RepositoryHistory repositoryHistory;

    public ResponseEntity<ModelHistory> historyGetOne(Integer historyID) {
        return new ResponseEntity<>(repositoryHistory.findById(historyID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelHistory>> historyGetAll() {
        return new ResponseEntity<>(repositoryHistory.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelHistory>> historyFind(
            Optional<LocalDate> historyDateMin,
            Optional<LocalDate> historyDateMax,
            Optional<LocalDate> historyTimeMin,
            Optional<LocalDate> historyTimeMax) {
        return new ResponseEntity<>(repositoryHistory.findHistory(historyDateMin, historyDateMax, historyTimeMin, historyTimeMax), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelHistory>> historyCreate(Iterable<ModelHistory> history) {
        for (ModelHistory a : history) {
            repositoryHistory.save(a);
        }

        return new ResponseEntity<>(repositoryHistory.findAll(), HttpStatus.OK);
    }

    public void historyCreate(String historyText) {
        ModelHistory newHistory = new ModelHistory();
        newHistory.setHistoryText(historyText);
        newHistory.setHistoryDate(LocalDate.now());
        newHistory.setHistoryTime(LocalTime.now());

        repositoryHistory.save(newHistory);
    }

    public ResponseEntity<String> historyDelete(Integer historyID) {
        ModelHistory history = repositoryHistory.findById(historyID).orElseThrow(RuntimeException::new);

        repositoryHistory.delete(history);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelHistory> historyPatch(JsonPatch patch, Integer historyID) throws JsonPatchException, JsonProcessingException {
        ModelHistory history = repositoryHistory.findById(historyID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(history, JsonNode.class)), ModelHistory.class), HttpStatus.OK);
    }
}
