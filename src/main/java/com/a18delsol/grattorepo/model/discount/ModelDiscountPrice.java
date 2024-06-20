package com.a18delsol.grattorepo.model.discount;

import com.a18delsol.grattorepo.model.user.ModelUserCart;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class ModelDiscountPrice extends ModelDiscount {
    private Float discountPrice;

    @Override public ModelUserCart processCart(ModelUserCart cart) {
        //cart.getCartItem().setItemPrice( cart.getCartItem().getItemPrice() - getDiscountPrice() );

        return cart;
    }
}
