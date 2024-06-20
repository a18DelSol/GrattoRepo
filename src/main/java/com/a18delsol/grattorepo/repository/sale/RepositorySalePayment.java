package com.a18delsol.grattorepo.repository.sale;

import com.a18delsol.grattorepo.model.sale.ModelSalePayment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositorySalePayment extends CrudRepository<ModelSalePayment, Integer> {
    @Query(nativeQuery=true,
    value="SELECT * FROM model_sale_payment"
    + " WHERE (:paymentName is null OR payment_name regexp :paymentName)")
    Iterable<ModelSalePayment> findSalePayment(
    Optional<String> paymentName
    );
}
