package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.user.ModelUser;
import com.a18delsol.grattorepo.model.user.ModelUserAttribute;
import com.a18delsol.grattorepo.repository.user.RepositoryUser;
import com.a18delsol.grattorepo.repository.user.RepositoryUserAttribute;
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
public class ServiceUser {
    @Autowired RepositoryUser          repositoryUser;
    @Autowired RepositoryUserAttribute repositoryUserAttribute;
    
    public ResponseEntity<ModelUser> userGetOne(Integer userID) {
        return new ResponseEntity<>(repositoryUser.findById(userID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelUser>> userGetAll() {
        return new ResponseEntity<>(repositoryUser.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelUser>> userFind(
            Optional<String> userName,
            Optional<String> userMail) {
        return new ResponseEntity<>(repositoryUser.findUser(userName, userMail), HttpStatus.OK);
    }

    public ResponseEntity<ModelUser> userCreate(ModelUser user) {
        repositoryUser.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<String> userDelete(Integer userID) {
        ModelUser user = repositoryUser.findById(userID).orElseThrow(RuntimeException::new);

        repositoryUser.delete(user);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelUser> userPatch(JsonPatch patch, Integer userID) throws JsonPatchException, JsonProcessingException {
        ModelUser user = repositoryUser.findById(userID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(user, JsonNode.class)), ModelUser.class), HttpStatus.OK);
    }

    public ResponseEntity<ModelUser> userSignUp(ModelUser user) {
        ModelUser userFind = repositoryUser.findByUserMail(user.getUserMail());

        if (userFind != null) {
            throw new RuntimeException();
        }

        repositoryUser.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<ModelUser> userSignIn(ModelUser user) {
        ModelUser userFind = repositoryUser.findByUserMail(user.getUserMail());

        if (!userFind.getUserPass().equals(user.getUserPass())) {
            throw new RuntimeException();
        }

        return new ResponseEntity<>(userFind, HttpStatus.OK);
    }

    //========================================================================
    // ModelUserAttribute sub-service
    //========================================================================

    public ResponseEntity<ModelUserAttribute> userAttributeGetOne(Integer userAttributeID) {
        return new ResponseEntity<>(repositoryUserAttribute.findById(userAttributeID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelUserAttribute>> userAttributeGetAll() {
        return new ResponseEntity<>(repositoryUserAttribute.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelUserAttribute>> userAttributeFind(
            Optional<String> userAttributeName) {
        return new ResponseEntity<>(repositoryUserAttribute.findUserAttribute(userAttributeName), HttpStatus.OK);
    }

    public ResponseEntity<ModelUserAttribute> userAttributeCreate(ModelUserAttribute userAttribute) {
        repositoryUserAttribute.save(userAttribute);

        return new ResponseEntity<>(userAttribute, HttpStatus.OK);
    }

    public ResponseEntity<String> userAttributeDelete(Integer userAttributeID) {
        ModelUserAttribute userAttribute = repositoryUserAttribute.findById(userAttributeID).orElseThrow(RuntimeException::new);

        repositoryUserAttribute.delete(userAttribute);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelUserAttribute> userAttributePatch(JsonPatch patch, Integer userAttributeID) throws JsonPatchException, JsonProcessingException {
        ModelUserAttribute userAttribute = repositoryUserAttribute.findById(userAttributeID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(userAttribute, JsonNode.class)), ModelUserAttribute.class), HttpStatus.OK);
    }
}