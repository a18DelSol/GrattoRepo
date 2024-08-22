package com.a18delsol.grattorepo.model.stock;

import com.a18delsol.grattorepo.model.item.ModelItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelStockEntry {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer entryID;

    private Integer entryCount;
    private Float   entryPrice;
    @ManyToOne @JsonIgnoreProperties("stockEntry") private ModelStock entryStock;
    @ManyToOne @JsonIgnoreProperties("itemEntry")  private ModelItem  entryItem;
}
