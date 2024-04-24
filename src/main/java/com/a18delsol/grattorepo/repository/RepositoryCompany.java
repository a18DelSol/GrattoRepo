package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelCompany;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryCompany extends CrudRepository<ModelCompany, Integer> {
    ModelCompany findByCompanyName(String companyName);
}
