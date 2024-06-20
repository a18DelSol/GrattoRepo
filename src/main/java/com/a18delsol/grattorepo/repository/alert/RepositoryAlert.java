package com.a18delsol.grattorepo.repository.alert;

import com.a18delsol.grattorepo.model.alert.ModelAlert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryAlert extends CrudRepository<ModelAlert, Integer> {
    @Query(nativeQuery=true,
    value="SELECT * FROM model_alert"
    + " WHERE (:alertName is null OR alert_name regexp :alertName)")
    Iterable<ModelAlert> findAlert(
    Optional<String> alertName);
}
