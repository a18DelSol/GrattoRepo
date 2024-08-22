package com.a18delsol.grattorepo.repository.history;

import com.a18delsol.grattorepo.model.history.ModelHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RepositoryHistory extends CrudRepository<ModelHistory, Integer> {
    @Query(nativeQuery=true,
    value="SELECT * FROM model_history"
    + " WHERE (:historyDateMin is null OR history_date >= :historyDateMin)"
    + " AND   (:historyDateMax is null OR history_date <= :historyDateMax)"
    + " AND   (:historyTimeMin is null OR history_time >= :historyTimeMin)"
    + " AND   (:historyTimeMax is null OR history_time <= :historyTimeMax)")
    Iterable<ModelHistory> findHistory(
    Optional<LocalDate> historyDateMin,
    Optional<LocalDate> historyDateMax,
    Optional<LocalDate> historyTimeMin,
    Optional<LocalDate> historyTimeMax
    );
}
