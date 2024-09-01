package com.a18delsol.grattorepo.model.sale;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer ID;

    private Float     salePrice;
    private Float     saleChange;
    private LocalDate saleDate;
    private LocalTime saleTime;

    @NotNull @Min(0) private Float        saleAmount;
    @NotNull         private String       saleName;
    @NotNull         private String       saleMail;
    @NotNull         private String       saleCall;
    @NotBlank        private String       salePayment;
    private DiscountType saleDiscountType;
    @NotNull         private Float        saleDiscountAmount;
    @OneToMany(cascade = CascadeType.ALL) private Set<ModelSaleOrder>  saleOrder;
    @OneToMany(cascade = CascadeType.ALL) private Set<ModelSaleUpdate> saleUpdate;

    public enum DiscountType {
        DISCOUNT_NONE,
        DISCOUNT_PERCENT,
        DISCOUNT_VALUE
    }
}