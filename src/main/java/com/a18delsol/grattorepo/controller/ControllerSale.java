package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelSale;
import com.a18delsol.grattorepo.repository.RepositoryDiscount;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.repository.RepositorySale;
import com.a18delsol.grattorepo.service.ServiceSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/saleController")
public class ControllerSale {
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryItem repositoryItem;
    @Autowired
    private RepositorySale repositorySale;
    @Autowired
    private RepositoryDiscount repositoryDiscount;

    @PostMapping(path="/sale/create")
    public @ResponseBody ModelSale saleCreate(@RequestBody ModelSale modelSaleData) {
        return ServiceSale.saleCreate(modelSaleData, repositoryUser, repositoryItem, repositorySale, repositoryDiscount);
    }

    @PostMapping(path="/sale/delete")
    public @ResponseBody ModelSale saleDelete(@RequestBody ModelSale modelSaleData) {
        return ServiceSale.saleDelete(modelSaleData, repositoryItem, repositorySale);
    }
}