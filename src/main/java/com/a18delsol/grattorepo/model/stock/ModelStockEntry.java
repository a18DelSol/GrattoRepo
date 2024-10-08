package com.a18delsol.grattorepo.model.stock;

import com.a18delsol.grattorepo.model.item.ModelItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelStockEntry {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer ID;

    @JsonIgnore private Boolean entityDelete = false;

    @NotNull private Integer entryCount;
    @NotNull private Float   entryPrice;
    @ManyToOne @JsonIgnoreProperties("itemEntry")  private ModelItem  entryItem;
    @ManyToOne @JsonIgnoreProperties("stockEntry") private ModelStock entryStock;
}
