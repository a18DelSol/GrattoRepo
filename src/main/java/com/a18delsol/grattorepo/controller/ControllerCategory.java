package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.repository.RepositoryCategory;
import com.a18delsol.grattorepo.service.ServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/categoryController")
public class ControllerCategory {
    @Autowired
    private RepositoryCategory repositoryCategory;

    @PostMapping(path="/category")
    public @ResponseBody ArrayList<ModelCategory> categoryCreate (@RequestBody ArrayList<ModelCategory> modelCategoryData) {
        ArrayList<ModelCategory> returnList = new ArrayList<>();

        for (ModelCategory a : modelCategoryData) {
            returnList.add(ServiceCategory.categoryCreate(a, repositoryCategory));
        }

        return returnList;
    }
}