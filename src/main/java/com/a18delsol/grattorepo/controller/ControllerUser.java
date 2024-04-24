package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelUser;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.a18delsol.grattorepo.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/userController")
public class ControllerUser {
    @Autowired
    private RepositoryUser repositoryUser;

    @PostMapping(path="/user/signUp")
    public @ResponseBody ModelUser controllerSignUp (@RequestBody ModelUser modelUserData) { return ServiceUser.userSignUp(modelUserData, repositoryUser); }

    @PostMapping(path="/user/signIn")
    public @ResponseBody ModelUser controllerSignIn (@RequestBody ModelUser modelUserData) { return ServiceUser.userSignIn(modelUserData, repositoryUser); }
}