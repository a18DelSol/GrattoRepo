package com.a18delsol.grattorepo.model.discount;

import com.a18delsol.grattorepo.model.item.ModelItemAttribute;
import com.a18delsol.grattorepo.model.item.ModelItemCompany;
import com.a18delsol.grattorepo.model.user.ModelUserAttribute;
import com.a18delsol.grattorepo.model.user.ModelUserCart;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "discountType")
@JsonSubTypes({
@JsonSubTypes.Type(value = ModelDiscountBuyGet.class,  name = "DISCOUNT_BUY_GET"),
@JsonSubTypes.Type(value = ModelDiscountPercent.class, name = "DISCOUNT_PERCENT"),
@JsonSubTypes.Type(value = ModelDiscountPrice.class,   name = "DISCOUNT_PRICE")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ModelDiscount {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer discountID;

    private String discountName;
    private LocalDate discountScheduleBegin;
    private LocalDate discountScheduleClose;
    @ManyToMany private Set<ModelUserAttribute> discountUserAttribute;
    @ManyToMany private Set<ModelItemAttribute> discountItemAttribute;
    @ManyToMany private Set<ModelItemCompany> discountItemCompany;

    public ModelUserCart processCart(ModelUserCart cart) {
        return null;
    }
}
