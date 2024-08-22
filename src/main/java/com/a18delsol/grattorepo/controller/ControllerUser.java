package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.user.ModelUser;
import com.a18delsol.grattorepo.model.user.ModelUserAttribute;
import com.a18delsol.grattorepo.service.ServiceUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/user")
public class ControllerUser {
    @Autowired
    private ServiceUser service;

    @GetMapping(path = "/{userID}")
    public @ResponseBody ResponseEntity<ModelUser> userGetOne(@PathVariable Integer userID) {
        return service.userGetOne(userID);
    }

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Iterable<ModelUser>> userGetAll() {
        return service.userGetAll();
    }

    @GetMapping(path="/find")
    public @ResponseBody ResponseEntity<Iterable<ModelUser>> userFind(
    @RequestParam Optional<String> userName,
    @RequestParam Optional<String> userMail) {
        return service.userFind(userName, userMail);
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<ModelUser> userCreate(@RequestBody @Valid ModelUser user) {
        return service.userCreate(user);
    }

    @DeleteMapping(path = "/{userID}")
    public @ResponseBody ResponseEntity<String> userDelete(@PathVariable Integer userID) {
        return service.userDelete(userID);
    }

    @PatchMapping(path = "/{userID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<ModelUser> userPatch(@RequestBody JsonPatch patch, @PathVariable Integer userID) throws JsonPatchException, JsonProcessingException {
        return service.userPatch(patch, userID);
    }

    //========================================================================

    @PostMapping(path="/signUp")
    public @ResponseBody ResponseEntity<ModelUser> userSignUp(@RequestBody ModelUser user) {
        return service.userSignUp(user);
    }

    @PostMapping(path="/signIn")
    public @ResponseBody ResponseEntity<ModelUser> userSignIn(@RequestBody ModelUser user) {
        return service.userSignIn(user);
    }

    //========================================================================
    // ModelUserAttribute sub-controller
    //========================================================================

    @GetMapping(path = "/attribute/{userAttributeID}")
    public @ResponseBody ResponseEntity<ModelUserAttribute> userAttributeGetOne(@PathVariable Integer userAttributeID) {
        return service.userAttributeGetOne(userAttributeID);
    }

    @GetMapping(path = "/attribute")
    public @ResponseBody ResponseEntity<Iterable<ModelUserAttribute>> userAttributeGetAll() {
        return service.userAttributeGetAll();
    }

    @GetMapping(path="/attribute/find")
    public @ResponseBody ResponseEntity<Iterable<ModelUserAttribute>> userAttributeFind(
    @RequestParam Optional<String> userAttributeName) {
        return service.userAttributeFind(userAttributeName);
    }

    @PostMapping(path = "/attribute")
    public @ResponseBody ResponseEntity<ModelUserAttribute> userAttributeCreate(@RequestBody @Valid ModelUserAttribute userAttribute) {
        return service.userAttributeCreate(userAttribute);
    }

    @DeleteMapping(path = "/attribute/{userAttributeID}")
    public @ResponseBody ResponseEntity<String> userAttributeDelete(@PathVariable Integer userAttributeID) {
        return service.userAttributeDelete(userAttributeID);
    }

    @PatchMapping(path = "/attribute/{userAttributeID}", consumes = "application/json-patch+json")
    public @ResponseBody ResponseEntity<ModelUserAttribute> userAttributePatch(@RequestBody JsonPatch patch, @PathVariable Integer userAttributeID)
    throws JsonPatchException, JsonProcessingException {
        return service.userAttributePatch(patch, userAttributeID);
    }
}
