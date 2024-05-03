package com.a18delsol.grattorepo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelItemCompany {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer companyID;

    private String companyName;
    @ManyToMany(mappedBy = "discountItemCompany") @JsonIgnore private Set<ModelDiscount> companyDiscount;
}
