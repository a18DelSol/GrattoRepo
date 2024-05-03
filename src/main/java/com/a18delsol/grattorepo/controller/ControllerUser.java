package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelUserCart;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.repository.RepositoryUserCart;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.a18delsol.grattorepo.service.ServiceUser;
import com.a18delsol.grattorepo.model.ModelUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(path="/user")
public class ControllerUser {
    @Autowired private RepositoryUser repositoryUser;
    @Autowired private RepositoryItem repositoryItem;
    @Autowired private RepositoryUserCart repositoryUserCart;

    @GetMapping(path="/")
    public @ResponseBody Iterable<ModelUser> userGet () {
        return repositoryUser.findAll();
    }

    @GetMapping(path="/{userID}")
    public @ResponseBody ModelUser userGet(@PathVariable Integer userID) {
        ModelUser modelUserFind = repositoryUser.findById(userID).orElse(null);

        if (modelUserFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        return modelUserFind;
    }

    @GetMapping(path="/find")
    public @ResponseBody Iterable<ModelUser> userFind (@RequestParam Optional<String> userName,
        @RequestParam Optional<String> userMail, @RequestParam Optional<String> userPass, @RequestParam Optional<LocalDate> userBirth) {
        return repositoryUser.findUser(userName, userMail, userPass, userBirth);
    }

    @PatchMapping(path="/{userID}", consumes = "application/json-patch+json")
    public @ResponseBody ModelUser userPatch(@PathVariable Integer userID, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        ModelUser modelUserFind = repositoryUser.findById(userID).orElse(null);

        if (modelUserFind == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        modelUserFind = ServiceUser.userPatch(patch, modelUserFind);

        repositoryUser.save(modelUserFind);

        return modelUserFind;
    }

    @PostMapping(path="/")
    public @ResponseBody ArrayList<ModelUser> userPost (@RequestBody ArrayList<ModelUser> modelUserData) {
        ArrayList<ModelUser> returnList = new ArrayList<>();

        for (ModelUser a : modelUserData) {
            returnList.add(ServiceUser.userCreate(a, repositoryUser));
        }

        return returnList;
    }

    /* EXCLUSIVE */

    @PostMapping(path="/signUp")
    public @ResponseBody ResponseEntity<String> userSignUp (@RequestBody ModelUser modelUserData) {
        return ServiceUser.userSignUp(modelUserData, repositoryUser);
    }

    @PostMapping(path="/signIn")
    public @ResponseBody ResponseEntity<ModelUser> userSignIn (@RequestBody ModelUser modelUserData) {
        return ServiceUser.userSignIn(modelUserData, repositoryUser);
    }

    @PostMapping(path="/{userID}/cart")
    public @ResponseBody ResponseEntity<ModelUser> userCartAttach (@PathVariable Integer userID, @RequestBody ModelUserCart modelUserCartData) {
        return ServiceUser.userCartAttach(userID, repositoryUser, repositoryItem, repositoryUserCart, modelUserCartData);
    }

    @DeleteMapping(path="/{userID}/cart/{cartID}")
    public @ResponseBody ResponseEntity<ModelUser> userCartDetach (@PathVariable Integer userID, @PathVariable Integer cartID) {
        return ServiceUser.userCartDetach(userID, repositoryUser, repositoryUserCart, cartID);
    }
}
