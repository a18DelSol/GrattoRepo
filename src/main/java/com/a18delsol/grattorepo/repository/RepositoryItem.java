package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryItem extends CrudRepository<ModelItem, Integer> {
    ModelItem findByItemName(String itemName);

    @Query(nativeQuery=true,
    value="SELECT * FROM model_item"
    + " WHERE (:itemName is null OR item_name regexp :itemName)"
    + " AND (:itemPriceMin is null OR item_price >= :itemPriceMin)"
    + " AND (:itemPriceMax is null OR item_price <= :itemPriceMax)"
    + " AND (:itemCountMin is null OR item_count >= :itemCountMin)"
    + " AND (:itemCountMax is null OR item_count <= :itemCountMax)"
    + " AND (:itemRestrict is null OR item_restrict = :itemRestrict)")
    Iterable<ModelItem> findItem(
    Optional<String> itemName,
    Optional<Float> itemPriceMin,
    Optional<Float> itemPriceMax,
    Optional<Integer> itemCountMin,
    Optional<Integer> itemCountMax,
    Optional<Boolean> itemRestrict);
}
