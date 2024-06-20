package com.a18delsol.grattorepo.repository.sale;

import com.a18delsol.grattorepo.model.sale.ModelSale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RepositorySale extends CrudRepository<ModelSale, Integer> {
    @Query(nativeQuery=true,
    value="SELECT * FROM model_sale"
    + " WHERE (:salePriceMin is null OR sale_price >= :salePriceMin)"
    + " AND   (:salePriceMax is null OR sale_price <= :salePriceMax)"
    + " AND   (:saleDateMin is null OR sale_date >= :saleDateMin)"
    + " AND   (:saleDateMax is null OR sale_date <= :saleDateMax)")
    Iterable<ModelSale> findSale(
    Optional<Float> salePriceMin,
    Optional<Float> salePriceMax,
    Optional<LocalDate> saleDateMin,
    Optional<LocalDate> saleDateMax
    );
}
