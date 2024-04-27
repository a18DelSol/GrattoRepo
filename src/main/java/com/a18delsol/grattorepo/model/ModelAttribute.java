package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
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
