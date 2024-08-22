package com.a18delsol.grattorepo.model.discount;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelDiscount {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer discountID;

    @NotBlank private String       discountName;
    @NotBlank private DiscountType discountType;
    @NotBlank private Float        discountAmount;

    enum DiscountType {
        DISCOUNT_NONE,
        DISCOUNT_PERCENT,
        DISCOUNT_VALUE
    }
}
