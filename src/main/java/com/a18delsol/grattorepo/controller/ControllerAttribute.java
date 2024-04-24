package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelAttribute;
import com.a18delsol.grattorepo.repository.RepositoryAttribute;
import com.a18delsol.grattorepo.service.ServiceAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(path="/attributeController")
public class ControllerAttribute {
    @Autowired
    private RepositoryAttribute repositoryAttribute;

    @PostMapping(path="/attribute")
    public @ResponseBody ArrayList<ModelAttribute> attributeCreate (@RequestBody ArrayList<ModelAttribute> modelAttributeData) {
        ArrayList<ModelAttribute> returnList = new ArrayList<>();

        for (ModelAttribute a : modelAttributeData) {
            returnList.add(ServiceAttribute.attributeCreate(a, repositoryAttribute));
        }

        return returnList;
    }

    @PatchMapping(path="/attribute")
    public @ResponseBody ModelAttribute attributeCreate (@RequestBody ModelAttribute modelAttributeData) {
        return null;
    }
}
