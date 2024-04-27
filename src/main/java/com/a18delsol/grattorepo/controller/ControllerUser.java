package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.a18delsol.grattorepo.request.RequestUserSignIn;
import com.a18delsol.grattorepo.request.RequestUserSignUp;
import com.a18delsol.grattorepo.service.ServiceUser;
import com.a18delsol.grattorepo.model.ModelUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(path="/userController")
public class ControllerUser {
    @Autowired
    private RepositoryUser repositoryUser;

    @GetMapping(path="/user")
    public @ResponseBody Iterable<ModelUser> userGet () {
        return repositoryUser.findAll();
    }

    @GetMapping(path="/user/{userID}")
    public @ResponseBody ModelUser userGet(@PathVariable Integer userID) {
        ModelUser modelUserFind = repositoryUser.findById(userID).orElse(null);

        if (modelUserFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        return modelUserFind;
    }

    @GetMapping(path="/user/find")
    public @ResponseBody Iterable<ModelUser> userFind (@RequestParam Optional<String> userName,
        @RequestParam Optional<String> userMail, @RequestParam Optional<String> userPass, @RequestParam Optional<LocalDate> userBirth) {
        return repositoryUser.findUser(userName, userMail, userPass, userBirth);
    }

    @PatchMapping(path="/user/{userID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelUser userPatch(@PathVariable Integer userID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelUser modelUserFind = repositoryUser.findById(userID).orElse(null);

        if (modelUserFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        modelUserFind = ServiceUser.userPatch(patch, modelUserFind);

        repositoryUser.save(modelUserFind);

        return modelUserFind;
    }

    @PostMapping(path="/user")
    public @ResponseBody ArrayList<ModelUser> userPost (@RequestBody ArrayList<ModelUser> modelUserData) {
        ArrayList<ModelUser> returnList = new ArrayList<>();

        for (ModelUser a : modelUserData) {
            returnList.add(ServiceUser.userCreate(a, repositoryUser));
        }

        return returnList;
    }

    /* EXCLUSIVE */

    @PostMapping(path="/user/signUp")
    public @ResponseBody ModelUser userSignUp (@RequestBody ModelUser modelUserData) {
        return ServiceUser.userSignUp(modelUserData, repositoryUser);
    }

    @PostMapping(path="/user/signIn")
    public @ResponseBody ModelUser userSignIn (@RequestBody ModelUser modelUserData) {
        return ServiceUser.userSignIn(modelUserData, repositoryUser);
    }
}
