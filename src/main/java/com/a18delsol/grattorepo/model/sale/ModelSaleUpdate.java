package com.a18delsol.grattorepo.model.sale;

import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelSaleUpdate {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer updateID;

    Integer updateCount;
    @ManyToOne ModelStockEntry updateEntry;
}
