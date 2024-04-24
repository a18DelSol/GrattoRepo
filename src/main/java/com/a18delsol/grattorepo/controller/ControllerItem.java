package com.a18delsol.grattorepo.controller;

import com.a18delsol.grattorepo.model.ModelItem;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.service.ServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/itemController")
public class ControllerItem {
    @Autowired
    private RepositoryItem repositoryItem;

    @GetMapping(path="/item")
    public @ResponseBody List<ModelItem> itemSearch (@RequestParam Optional<String> itemName,
    @RequestParam Optional<Float> itemPriceMore, @RequestParam Optional<Float> itemPriceLess) {
        return null;
    }

    @PostMapping(path="/item")
    public @ResponseBody ArrayList<ModelItem> itemCreate (@RequestBody ArrayList<ModelItem> modelItemData) {
        ArrayList<ModelItem> returnList = new ArrayList<>();

        for (ModelItem a : modelItemData) {
            returnList.add(ServiceItem.itemCreate(a, repositoryItem));
        }

        return returnList;
    }
}