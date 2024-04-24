package com.a18delsol.grattorepo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelAttribute {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer attributeID;
    private String attributeName;
}
