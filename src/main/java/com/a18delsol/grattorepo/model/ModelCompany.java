package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelCompany {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer companyID;

    private String companyName;

    //@JsonIgnore -> prevenir recursion.
}
