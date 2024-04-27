package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelSale;
import com.a18delsol.grattorepo.model.ModelItem;
import com.a18delsol.grattorepo.model.ModelSale;
import com.a18delsol.grattorepo.repository.RepositorySale;
import com.a18delsol.grattorepo.repository.RepositoryItem;
import com.a18delsol.grattorepo.repository.RepositorySale;
import com.a18delsol.grattorepo.repository.RepositoryDiscount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

    public static ModelSale salePurchase(ModelSale sale, RepositorySale repository) {
        sale.setSalePrice(sale.getSaleItem().getItemPrice());
        sale.setSaleDate(LocalDate.now());
        sale.setSaleTime(LocalTime.now());

        repository.save(sale);

        return sale;
    }
}
