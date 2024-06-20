package com.a18delsol.grattorepo.repository.sale;

import com.a18delsol.grattorepo.model.sale.ModelSaleOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositorySaleOrder extends CrudRepository<ModelSaleOrder, Integer> {
    @Query(nativeQuery=true,
            value="SELECT * FROM model_sale_order"
                    + " WHERE (:orderAmountMin is null OR order_amount >= :orderAmountMin)"
                    + " AND   (:orderAmountMax is null OR order_amount <= :orderAmountMax)")
    Iterable<ModelSaleOrder> findSaleOrder(
    Optional<Float> orderAmountMin,
    Optional<Float> orderAmountMax
    );
}
