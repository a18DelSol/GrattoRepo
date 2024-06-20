package com.a18delsol.grattorepo.model.item;

import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelItem {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer itemID;

    private String  itemName;
    private String  itemCode;
    private Integer itemCount;
    @ManyToMany private Set<ModelItemAttribute> itemAttribute;
    @ManyToOne  private ModelItemCompany        itemCompany;
    @OneToMany(mappedBy = "entryItem") @JsonIgnoreProperties("entryItem") private Set<ModelStockEntry> itemEntry;
}