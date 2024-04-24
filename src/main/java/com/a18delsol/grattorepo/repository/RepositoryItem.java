package com.a18delsol.grattorepo.repository;

import com.a18delsol.grattorepo.model.ModelItem;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RepositoryItem extends CrudRepository<ModelItem, Integer> {
    ModelItem findByItemName(String itemName);
}
