package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.contact.ModelContact;
import com.a18delsol.grattorepo.repository.contact.RepositoryContact;
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
public class ServiceContact {
    @Autowired
    RepositoryContact repositoryContact;

    public ResponseEntity<ModelContact> contactGetOne(Integer contactID) {
        return new ResponseEntity<>(repositoryContact.findById(contactID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelContact>> contactGetAll() {
        return new ResponseEntity<>(repositoryContact.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelContact>> contactFind(
            Optional<String> contactName,
            Optional<String> contactMail,
            Optional<String> contactCall) {
        return new ResponseEntity<>(repositoryContact.findContact(contactName, contactMail, contactCall), HttpStatus.OK);
    }

    public ResponseEntity<String> contactCreate(ModelContact contact) {
        repositoryContact.save(contact);

        return new ResponseEntity<>("Creation OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> contactDelete(Integer contactID) {
        ModelContact contact = repositoryContact.findById(contactID).orElseThrow(RuntimeException::new);

        repositoryContact.delete(contact);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelContact> contactPatch(JsonPatch patch, Integer contactID) throws JsonPatchException, JsonProcessingException {
        ModelContact contact = repositoryContact.findById(contactID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(contact, JsonNode.class)), ModelContact.class), HttpStatus.OK);
    }
}
