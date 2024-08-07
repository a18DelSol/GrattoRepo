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
public class ModelItemCompany {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer companyID;

    @NotBlank private String companyName;
    @ManyToMany(mappedBy = "discountItemCompany") @JsonIgnore private Set<ModelDiscount> companyDiscount;
    @OneToMany(mappedBy = "itemCompany") @JsonIgnore private Set<ModelItem> companyItem;
}
