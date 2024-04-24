package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelUser;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class ServiceUser {
    public static ModelUser userSignUp(ModelUser modelUser, RepositoryUser repository) {
        ModelUser modelUserSet = repository.findByUserMail(modelUser.getUserMail());

        if (modelUser.getUserName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelUser name cannot be blank.");
        } else if (modelUser.getUserMail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelUser mail cannot be blank.");
        } else if (modelUser.getUserPass().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelUser pass cannot be blank.");
        } else if (modelUserSet != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelUser mail is already in use.");
        }

        repository.save(modelUser);

        return modelUser;
    }

    public static ModelUser userSignIn(ModelUser modelUser, RepositoryUser repository) {
        ModelUser modelUserFind = repository.findByUserMail(modelUser.getUserMail());

        if (modelUserFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ModelUser not found.");
        } else {
            if (!Objects.equals(modelUser.getUserPass(), modelUserFind.getUserPass())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ModelUser pass is incorrect.");
            }
        }

        return modelUserFind;
    }
}
