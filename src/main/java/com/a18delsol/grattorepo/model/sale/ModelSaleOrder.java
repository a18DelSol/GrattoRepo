package com.a18delsol.grattorepo.model.sale;

import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelSaleOrder {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer ID;

    @NotNull @Min(0)    private Integer         orderAmount;
    @NotNull @ManyToOne private ModelStockEntry orderEntry;
}
