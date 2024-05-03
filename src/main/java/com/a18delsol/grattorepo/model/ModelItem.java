package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelItem {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer itemID;

    private String itemName;
    private Float itemPrice;
    private Integer itemCount;
    private Boolean itemRestrict;
    @ManyToMany private Set<ModelItemAttribute> itemAttribute;
    @ManyToOne @JoinTable(name = "item_company_join") private ModelItemCompany itemCompany;
}
