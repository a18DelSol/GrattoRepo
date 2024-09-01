package com.a18delsol.grattorepo.model.item;

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
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer ID;

    @JsonIgnore private Boolean entityDelete = false;

    @NotBlank private String attributeName;

    @ManyToMany(mappedBy = "itemAttribute") @JsonIgnore
    private Set<ModelItem> attributeItem;
}
