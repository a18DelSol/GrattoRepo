package com.a18delsol.grattorepo.repository.history;

import com.a18delsol.grattorepo.model.history.ModelHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryHistory extends CrudRepository<ModelHistory, Integer> {
    @Query(nativeQuery=true,
    value="SELECT * FROM model_history"
    + " WHERE (:historyText is null OR history_text regexp :historyText)")
    Iterable<ModelHistory> findHistory(
    Optional<String> historyText);
}
