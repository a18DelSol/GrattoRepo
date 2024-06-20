package com.a18delsol.grattorepo.model.sale;

import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelSaleOrder {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer orderID;

    private Integer orderAmount;
    @ManyToOne private ModelStockEntry orderEntry;
}
