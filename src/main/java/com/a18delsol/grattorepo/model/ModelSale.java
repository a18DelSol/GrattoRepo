package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class ModelSale {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer saleID;

    private Float salePrice;
    private LocalDate saleDate;
    private LocalTime saleTime;
    private Payment salePayment;
    @ManyToOne  private ModelUser saleUser;
    @ManyToMany private Set<ModelUserCart> saleCart;
    @ManyToMany private Set<ModelDiscount> saleDiscountInclude;
    @ManyToMany private Set<ModelDiscount> saleDiscountExclude;

    public enum Payment {
        CASH,
        CREDIT,
        DEBIT
    }
}
