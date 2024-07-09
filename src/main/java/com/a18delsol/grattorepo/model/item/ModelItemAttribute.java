package com.a18delsol.grattorepo.model.item;

import com.a18delsol.grattorepo.model.discount.ModelDiscount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelItemAttribute {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer attributeID;

    @NotBlank private String attributeName;
    @ManyToMany(mappedBy = "discountItemAttribute") @JsonIgnore private Set<ModelDiscount> attributeDiscount;
}
