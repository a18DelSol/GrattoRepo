package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.model.ModelCompany;
import com.a18delsol.grattorepo.model.ModelDiscount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.Set;

public interface RepositoryDiscount extends CrudRepository<ModelDiscount, Integer> {
    ModelDiscount findByDiscountName(String discountName);

    @Query(nativeQuery=true,
    value="SELECT * FROM model_discount"
    + " WHERE (:discountName is null OR discount_name regexp :discountName)"
    + " AND (:discountPercentMin is null OR discount_percent >= :discountPercentMin)"
    + " AND (:discountPercentMax is null OR discount_percent <= :discountPercentMax)")
    Iterable<ModelDiscount> findDiscount(Optional<String> discountName, Optional<Float> discountPercentMin, Optional<Float> discountPercentMax);
}
