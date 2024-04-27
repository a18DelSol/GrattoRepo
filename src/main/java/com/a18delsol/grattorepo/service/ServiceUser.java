package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelUser;
import com.a18delsol.grattorepo.model.ModelUser;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.a18delsol.grattorepo.request.RequestUserSignUp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

public class ServiceUser {
    /* GENERAL */

    public static ModelUser userCreate(ModelUser user, RepositoryUser repository) {
        repository.save(user);

        return user;
    }

    public static ModelUser userPatch(JsonPatch patch, ModelUser user) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(user, JsonNode.class)), ModelUser.class);
    }

    /* EXCLUSIVE */

    public static ModelUser userSignUp(ModelUser user, RepositoryUser repository) {
        ModelUser modelUserFind = repository.findByUserMail(user.getUserMail());

        if (modelUserFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail already in use.");
        }

        repository.save(user);

        return user;
    }

    public static ModelUser userSignIn(ModelUser user, RepositoryUser repository) {
        ModelUser modelUserFind = repository.findByUserMail(user.getUserMail());

        if (modelUserFind == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.");
        } else if (!modelUserFind.getUserPass().equals(user.getUserPass())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pass is incorrect.");
        }

        return modelUserFind;
    }
}
