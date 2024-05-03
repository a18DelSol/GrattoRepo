package com.a18delsol.grattorepo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelItemAttribute {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer attributeID;

    private String attributeName;
    @ManyToMany(mappedBy = "discountItemAttribute") @JsonIgnore private Set<ModelDiscount> attributeDiscount;
}
