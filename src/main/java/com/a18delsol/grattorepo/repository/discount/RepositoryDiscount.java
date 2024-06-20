package com.a18delsol.grattorepo.repository.discount;

import com.a18delsol.grattorepo.model.discount.ModelDiscount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryDiscount extends CrudRepository<ModelDiscount, Integer> {
    ModelDiscount findByDiscountName(String discountName);

    @Query(nativeQuery=true,
    value="SELECT * FROM model_discount"
    + " WHERE (:discountName is null OR discount_name regexp :discountName)")
    Iterable<ModelDiscount> findDiscount(Optional<String> discountName);
}
