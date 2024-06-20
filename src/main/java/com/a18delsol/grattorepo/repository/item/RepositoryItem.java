package com.a18delsol.grattorepo.repository.item;

import com.a18delsol.grattorepo.model.item.ModelItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryItem extends CrudRepository<ModelItem, Integer> {
    Optional<ModelItem> findByItemName(String itemName);
    Optional<ModelItem> findByItemCode(String itemCode);

    @Query(nativeQuery=true,
    value="SELECT * FROM model_item"
    + " WHERE (:itemName is null OR item_name regexp :itemName)"
    + " AND   (:itemCode is null OR item_code regexp :itemCode)"
    + " AND   (:itemRestrict is null OR item_restrict = :itemRestrict)")
    Iterable<ModelItem> findItem(
    Optional<String> itemName,
    Optional<String> itemCode);
}
