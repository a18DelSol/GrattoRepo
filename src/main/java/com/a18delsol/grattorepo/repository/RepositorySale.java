package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelDiscount;
import com.a18delsol.grattorepo.model.ModelItem;
import com.a18delsol.grattorepo.model.ModelSale;
import com.a18delsol.grattorepo.model.ModelUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositorySale extends CrudRepository<ModelSale, Integer> {
    @Query(nativeQuery=true,
    value="SELECT * FROM model_sale"
    + "WHERE (:saleUserID is null OR saleUser.userID regexp :saleUserID)")
    Iterable<ModelSale> findSale(Integer saleUserID);
}
