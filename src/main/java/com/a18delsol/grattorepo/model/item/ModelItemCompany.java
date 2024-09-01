package com.a18delsol.grattorepo.model.item;

import com.a18delsol.grattorepo.model.contact.ModelContact;
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
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer ID;

    @JsonIgnore private Boolean entityDelete = false;

    @NotBlank private String companyName;

    @OneToMany(mappedBy = "itemCompany") @JsonIgnore
    private Set<ModelItem> companyItem;

    @ManyToMany(mappedBy = "contactCompany") @JsonIgnore
    private Set<ModelContact> companyContact;
}
