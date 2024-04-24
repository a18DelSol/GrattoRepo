package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelUser;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryUser extends CrudRepository<ModelUser, Integer> {
    ModelUser findByUserMail(String userMail);
}
