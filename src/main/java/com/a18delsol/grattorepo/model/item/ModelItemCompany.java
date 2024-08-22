package com.a18delsol.grattorepo.model.item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelItemCompany {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer companyID;

    @NotBlank private String companyName;
}
