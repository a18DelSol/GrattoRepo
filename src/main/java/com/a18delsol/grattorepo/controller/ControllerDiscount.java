package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelDiscount;
import com.a18delsol.grattorepo.repository.RepositoryDiscount;
import com.a18delsol.grattorepo.service.ServiceDiscount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/discountController")
public class ControllerDiscount {
    @Autowired
    private RepositoryDiscount repositoryDiscount;

    @PostMapping(path="/discount")
    public @ResponseBody ArrayList<ModelDiscount> discountCreate (@RequestBody ArrayList<ModelDiscount> modelDiscountData) {
        ArrayList<ModelDiscount> returnList = new ArrayList<>();

        for (ModelDiscount a : modelDiscountData) {
            returnList.add(ServiceDiscount.discountCreate(a, repositoryDiscount));
        }

        return returnList;
    }
}
