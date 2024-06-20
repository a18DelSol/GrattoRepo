package com.a18delsol.grattorepo.model.user;

import com.a18delsol.grattorepo.model.discount.ModelDiscount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelUserAttribute {
    @Id @GeneratedValue(strategy=GenerationType.AUTO) private Integer attributeID;

    private String attributeName;
    @ManyToMany(mappedBy = "discountUserAttribute") @JsonIgnore private Set<ModelDiscount> attributeDiscount;
}
