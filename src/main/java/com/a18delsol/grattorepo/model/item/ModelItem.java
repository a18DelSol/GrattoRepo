package com.a18delsol.grattorepo.model.item;

import com.a18delsol.grattorepo.model.contact.ModelContact;
import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer ID;

    @JsonIgnore private Boolean entityDelete = false;

    @NotBlank            private String  itemName;
    @NotBlank            private String  itemCode;
    @NotBlank            private String  itemSKU;
    @NotNull @Min(0)     private Integer itemCount;
    @NotNull @Min(0)     private Integer itemAlert;
    @NotNull @ManyToMany private Set<ModelItemAttribute> itemAttribute;
    @NotNull @ManyToOne  private ModelItemCompany        itemCompany;

    @ManyToMany(mappedBy = "contactItem") @JsonIgnore
    private Set<ModelContact> itemContact;

    @OneToMany(mappedBy = "entryItem") @JsonIgnoreProperties("entryItem")
    private Set<ModelStockEntry> itemEntry;
}