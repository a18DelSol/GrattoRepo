package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelUser;
import com.a18delsol.grattorepo.model.ModelItem;
import com.a18delsol.grattorepo.model.ModelSale;
import com.a18delsol.grattorepo.repository.RepositoryUser;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.repository.RepositorySale;
import com.a18delsol.grattorepo.repository.RepositoryDiscount;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ServiceSale {
    public static ModelSale saleCreate(ModelSale modelSale, RepositoryUser repositoryUser, RepositoryItem repositoryItem, RepositorySale repositorySale, RepositoryDiscount repositoryDiscount) {
        /*
        Optional<ModelUser> userFind = repositoryUser.findById(modelSale.getSaleModelUser().getUserID());
        Optional<ModelItem> itemFind = repositoryItem.findById(modelSale.getSaleModelItem().getItemID());
        ModelUser modelUserGet = userFind.orElse(null);
        ModelItem modelItemGet = itemFind.orElse(null);

        if (modelUserGet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ModelUser not found.");
        }

        Integer userYear = modelUserGet.getUserBirth().getYear();
        Integer dateYear = LocalDate.now().getYear();

        if (modelItemGet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ModelItem not found.");
        } else if (modelSale.getSaleCount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelItem count cannot be lower than or equal to zero.");
        } else if (modelItemGet.getItemCount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelItem is currently out of stock.");
        } else if (modelItemGet.getItemCount() - modelSale.getSaleCount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelItem stock is not above or equal to the purchase count.");
        } else if (modelItemGet.getItemRestrict() && dateYear - userYear < 18) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelUser cannot legally purchase item.");
        }

        Float totalPrice = modelItemGet.getItemPrice();

        modelItemGet.setItemCount(modelItemGet.getItemCount() - modelSale.getSaleCount());
        repositoryItem.save(modelItemGet);

        modelSale.setSaleModelUser(modelUserGet);
        modelSale.setSaleModelItem(modelItemGet);
        modelSale.setSalePrice(totalPrice);
        modelSale.setSaleDate(LocalDate.now().toString());
        modelSale.setSaleTime(LocalTime.now().toString());

        repositorySale.save(modelSale);
        */

        return modelSale;
    }

    public static ModelSale saleDelete(ModelSale modelSale, RepositoryItem repositoryItem, RepositorySale repositorySale) {
        /*
        Optional<ModelSale> saleFind = repositorySale.findById(modelSale.getSaleID());
        ModelSale modelSaleGet = saleFind.orElse(null);

        if (modelSaleGet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ModelSale not found.");
        }

        ModelItem modelItemGet = modelSaleGet.getSaleModelItem();

        if (modelItemGet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ModelItem not found.");
        }

        modelItemGet.setItemCount(modelItemGet.getItemCount() + modelSaleGet.getSaleCount());
        repositoryItem.save(modelItemGet);

        repositorySale.delete(modelSaleGet);
        */

        return modelSale;
    }
}
