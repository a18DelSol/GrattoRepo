package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelItem;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceItem {
    public static ModelItem itemCreate(ModelItem modelItem, RepositoryItem repository) {
        ModelItem modelItemSet = repository.findByItemName(modelItem.getItemName());

        if (modelItem.getItemName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelItem name cannot be blank.");
        } else if (modelItem.getItemPrice() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelItem price cannot be lower than zero.");
        } else if (modelItem.getItemCount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelItem count cannot be lower than zero.");
        } else if (modelItemSet != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelItem is already in use.");
        }

        repository.save(modelItem);

        return modelItem;
    }
}
