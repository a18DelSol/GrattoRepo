package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelCategory;
import com.a18delsol.grattorepo.model.ModelCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryCompany extends CrudRepository<ModelCompany, Integer> {
    ModelCompany findByCompanyName(String companyName);

    @Query(nativeQuery=true, value="SELECT * FROM model_company WHERE company_name regexp :companyName")
    Iterable<ModelCompany> findCompany(String companyName);
}
