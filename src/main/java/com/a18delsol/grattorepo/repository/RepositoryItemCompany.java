package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelItemCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryItemCompany extends CrudRepository<ModelItemCompany, Integer> {
    ModelItemCompany findByCompanyName(String companyName);

    @Query(nativeQuery=true, value="SELECT * FROM model_item_company WHERE company_name regexp :companyName")
    Iterable<ModelItemCompany> findCompany(String companyName);
}
