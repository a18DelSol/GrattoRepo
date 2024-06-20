package com.a18delsol.grattorepo.repository.stock;

import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryStockEntry extends CrudRepository<ModelStockEntry, Integer> {
    @Query(nativeQuery=true,
    value="SELECT * FROM model_stock_entry"
    + " WHERE (:entryCountMin is null OR entry_count >= :entryCountMin)"
    + " AND   (:entryCountMax is null OR entry_count <= :entryCountMax)"
    + " AND   (:entryPriceMin is null OR entry_price <= :entryPriceMin)"
    + " AND   (:entryPriceMax is null OR entry_price <= :entryPriceMax)")
    Iterable<ModelStockEntry> findStockEntry(
    Optional<Integer> entryCountMin,
    Optional<Integer> entryCountMax,
    Optional<Float> entryPriceMin,
    Optional<Float> entryPriceMax);
}
