package com.a18delsol.grattorepo.repository.stock;

import com.a18delsol.grattorepo.model.stock.ModelStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryStock extends CrudRepository<ModelStock, Integer> {
    ModelStock findByStockName(String stockName);

    @Query(nativeQuery=true,
    value="SELECT * FROM model_stock"
    + " WHERE (:stockName is null OR stock_name regexp :stockName)")
    Iterable<ModelStock> findStock(Optional<String> stockName);
}
