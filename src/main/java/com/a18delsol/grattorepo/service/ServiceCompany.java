package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.ModelCompany;
import com.a18delsol.grattorepo.repository.RepositoryCompany;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceCompany {
    public static ModelCompany companyCreate(ModelCompany company, RepositoryCompany repository) {
        ModelCompany modelCompanyFind = repository.findByCompanyName(company.getCompanyName());

        if (modelCompanyFind != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already in use.");
        }

        repository.save(company);

        return company;
    }
}
