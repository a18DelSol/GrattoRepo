package com.a18delsol.grattorepo.repository.item;

import com.a18delsol.grattorepo.model.item.ModelItemCompany;
import com.a18delsol.grattorepo.model.item.ModelItemCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryItemCompany extends CrudRepository<ModelItemCompany, Integer> {
    ModelItemCompany findByCompanyName(String companyName);

    @Query(nativeQuery=true,
    value="SELECT * FROM model_item_company"
    + " WHERE (:companyName is null OR company_name regexp :companyName)")
    Iterable<ModelItemCompany> findItemCompany(Optional<String> companyName);
}
