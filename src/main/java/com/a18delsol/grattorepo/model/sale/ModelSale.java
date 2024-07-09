package com.a18delsol.grattorepo.model.sale;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @Min(0) private Float saleAmount;
    private Float  saleChange;
    private String saleName;
    private String saleMail;
    private String saleCall;
    private LocalDate saleDate;
    private LocalTime saleTime;
    @NotNull @ManyToOne private ModelSalePayment    salePayment;
    @NotNull @OneToMany private Set<ModelSaleOrder> saleOrder;
}