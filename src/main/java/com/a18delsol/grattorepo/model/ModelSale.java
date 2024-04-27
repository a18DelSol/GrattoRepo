package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class ModelSale {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer saleID;

    private Float salePrice;
    private Integer saleCount;
    private LocalDate saleDate;
    private LocalTime saleTime;
    @ManyToOne
    private ModelUser saleUser;
    @ManyToOne
    private ModelItem saleItem;
}
