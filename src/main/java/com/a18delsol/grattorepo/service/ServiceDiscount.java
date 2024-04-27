package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelDiscount;
import com.a18delsol.grattorepo.model.ModelDiscount;
import com.a18delsol.grattorepo.repository.RepositoryDiscount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceDiscount {
    public static ModelDiscount discountCreate(ModelDiscount discount, RepositoryDiscount repository) {
        /*
        ModelDiscount modelDiscountFind = repository.findByDiscountName(discount.getDiscountName());

        if (modelDiscountFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        } else if (discount.getDiscountScheduleBegin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Begin date cannot be blank.");
        } else if (discount.getDiscountScheduleClose() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Close date cannot be blank.");
        } else if (discount.getDiscountPercent() <= 0 || discount.getDiscountPercent() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelDiscount percent cannot be lower than or equal to 0, or higher than 100.");
        } else if (discount.getDiscountItem() == null && discount.getDiscountAttribute() == null && discount.getDiscountDiscount() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ModelDiscount item, attribute, and category cannot be empty.");
        }
        */

        repository.save(discount);

        return discount;
    }

    public static ModelDiscount discountPatch(JsonPatch patch, ModelDiscount discount) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(discount, JsonNode.class)), ModelDiscount.class);
    }
}
