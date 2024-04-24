package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelSale {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer saleID;

    private Float salePrice;
    private Integer saleCount;
    private String saleDate;
    private String saleTime;
}
