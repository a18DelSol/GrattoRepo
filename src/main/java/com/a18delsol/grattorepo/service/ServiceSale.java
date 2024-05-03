package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.*;
import com.a18delsol.grattorepo.model.ModelSale;
import com.a18delsol.grattorepo.repository.*;
import com.a18delsol.grattorepo.repository.RepositorySale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ServiceSale {
    public static ModelSale saleCreate(ModelSale sale, RepositorySale repository) {
        repository.save(sale);

        return sale;
    }

    public static ModelSale salePatch(JsonPatch patch, ModelSale sale) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(sale, JsonNode.class)), ModelSale.class);
    }

    /* EXCLUSIVE */

    public static ModelSale saleCheckOut(Integer userID, RepositorySale repositorySale, RepositoryUser repositoryUser) {
        return null;
    }

    public static ModelSale salePurchase(Integer userID, RepositorySale repositorySale, RepositoryUser repositoryUser, RepositoryItem repositoryItem,
        ModelSale sale) {
        Optional<ModelUser> userFind = repositoryUser.findById(userID);

        if (userFind.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        ModelUser user = userFind.get();

        if (user.getUserCart().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User cart is empty.");
        }

        Set<ModelDiscount> saleDiscount = new HashSet<>();
        Float salePrice = 0.0F;

        for (ModelUserCart c : user.getUserCart()) {
            ModelItem cartItem = c.getCartItem();

            salePrice += cartItem.getItemPrice() * c.getCartCount();

            cartItem.setItemCount(cartItem.getItemCount() - c.getCartCount());
            repositoryItem.save(cartItem);

            for (ModelUserAttribute a : user.getUserAttribute()) {
                for (ModelDiscount d : a.getAttributeDiscount()) {
                    if (ServiceDiscount.discountCheck(d, sale.getSaleDiscountExclude())) {
                        salePrice *= (100 - d.getDiscountPercent()) / 100;
                        saleDiscount.add(d);
                    }
                }
            }

            for (ModelItemAttribute a : cartItem.getItemAttribute()) {
                for (ModelDiscount d : a.getAttributeDiscount()) {
                    if (ServiceDiscount.discountCheck(d, sale.getSaleDiscountExclude())) {
                        salePrice *= (100 - d.getDiscountPercent()) / 100;
                        saleDiscount.add(d);
                    }
                }
            }

            for (ModelDiscount d : cartItem.getItemCompany().getCompanyDiscount()) {
                if (ServiceDiscount.discountCheck(d, sale.getSaleDiscountExclude())) {
                    salePrice *= (100 - d.getDiscountPercent()) / 100;
                    saleDiscount.add(d);
                }
            }
        }

        Set<ModelUserCart> saleCart = new HashSet<>(user.getUserCart());

        sale.setSaleUser(user);
        sale.setSaleCart(saleCart);
        sale.setSalePrice(salePrice);
        sale.setSaleDate(LocalDate.now());
        sale.setSaleTime(LocalTime.now());
        sale.setSaleDiscountInclude(saleDiscount);
        repositorySale.save(sale);

        user.getUserCart().clear();
        repositoryUser.save(user);

        return sale;
    }
}
