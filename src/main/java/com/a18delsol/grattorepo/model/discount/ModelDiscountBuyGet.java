package com.a18delsol.grattorepo.model.discount;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class ModelDiscountBuyGet extends ModelDiscount {
    private Integer discountBuyAmount;
    private Integer discountGetAmount;
}