package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer itemID;

    private String itemName;
    private Float itemPrice;
    private Integer itemCount;
    private Boolean itemRestrict;
    @OneToOne(mappedBy = "companyItem")
    private ModelCompany itemCompany;
}
