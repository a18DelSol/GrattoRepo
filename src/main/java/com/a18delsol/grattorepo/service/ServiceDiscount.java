package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.*;
import com.a18delsol.grattorepo.model.ModelDiscount;
import com.a18delsol.grattorepo.repository.RepositoryDiscount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class ServiceDiscount {
    public static ModelDiscount discountCreate(ModelDiscount discount, RepositoryDiscount repository) {
        repository.save(discount);

        return discount;
    }

    public static ModelDiscount discountPatch(JsonPatch patch, ModelDiscount discount) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(discount, JsonNode.class)), ModelDiscount.class);
    }

    public static Boolean discountCheck(ModelDiscount discount, Set<ModelDiscount> discountExclude) {
        LocalDate now = LocalDate.now();

        Boolean exclude = true;

        if (discountExclude != null) {
            exclude = discountExclude.stream().noneMatch(d -> Objects.equals(d.getDiscountID(), discount.getDiscountID()));
        }

        return now.toEpochDay() >= discount.getDiscountScheduleBegin().toEpochDay() &&
               now.toEpochDay() <= discount.getDiscountScheduleClose().toEpochDay() &&
               exclude;
    }

}
