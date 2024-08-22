package com.a18delsol.grattorepo.model.item;

import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelItem {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer itemID;

    @NotBlank private String  itemName;
    @NotBlank private String  itemCode;
    @NotNull @Min(0) private Integer itemCount;
    @NotNull @Min(0) private Integer itemAlert;
    @ManyToMany private Set<ModelItemAttribute> itemAttribute;
    @ManyToOne  private ModelItemCompany        itemCompany;
    @OneToMany(mappedBy = "entryItem") @JsonIgnoreProperties("entryItem")
    private Set<ModelStockEntry> itemEntry;
}