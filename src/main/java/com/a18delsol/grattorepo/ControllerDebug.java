package com.a18delsol.grattorepo;

import com.a18delsol.grattorepo.model.ModelAttribute;
import com.a18delsol.grattorepo.repository.*;
import com.a18delsol.grattorepo.service.ServiceAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/debugController")
public class ControllerDebug {
    @Autowired
    private RepositoryAttribute repositoryAttribute;
    @Autowired
    private RepositoryCategory repositoryCategory;
    @Autowired
    private RepositoryCompany repositoryCompany;
    @Autowired
    private RepositoryDiscount repositoryDiscount;
    @Autowired
    private RepositoryItem repositoryItem;
    @Autowired
    private RepositorySale repositorySale;
    @Autowired
    private RepositoryUser repositoryUser;

    @PostMapping(path="/debug")
    public @ResponseBody String debugDelete () {
        repositoryAttribute.deleteAll();
        repositoryCategory.deleteAll();
        repositoryCompany.deleteAll();
        repositoryDiscount.deleteAll();
        repositoryItem.deleteAll();
        repositorySale.deleteAll();
        repositoryUser.deleteAll();

        return "bye-bye";
    }
}
