package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelCompany;
import com.a18delsol.grattorepo.repository.RepositoryCompany;
import com.a18delsol.grattorepo.service.ServiceCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/categoryCompany")
public class ControllerCompany {
    @Autowired
    private RepositoryCompany repositoryCompany;

    @PostMapping(path="/company")
    public @ResponseBody ArrayList<ModelCompany> categoryCreate (@RequestBody ArrayList<ModelCompany> modelCompanyData) {
        ArrayList<ModelCompany> returnList = new ArrayList<>();

        for (ModelCompany a : modelCompanyData) {
            returnList.add(ServiceCompany.companyCreate(a, repositoryCompany));
        }

        return returnList;
    }
}
