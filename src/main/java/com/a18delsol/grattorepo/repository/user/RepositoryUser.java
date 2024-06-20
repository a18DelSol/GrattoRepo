package com.a18delsol.grattorepo.repository.user;

import com.a18delsol.grattorepo.model.user.ModelUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryUser extends CrudRepository<ModelUser, Integer> {
    ModelUser findByUserName(String userName);
    ModelUser findByUserMail(String userMail);

    @Query(nativeQuery=true,
    value="SELECT * FROM model_user"
    + " WHERE (:userName is null OR user_name regexp :userName)"
    + " AND   (:userMail is null OR user_mail regexp :userMail)")
    Iterable<ModelUser> findUser(
    Optional<String> userName,
    Optional<String> userMail);
}
