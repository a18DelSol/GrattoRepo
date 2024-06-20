package com.a18delsol.grattorepo.model.user;

import com.a18delsol.grattorepo.model.item.ModelItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelUserCart {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer cartID;

    private Integer cartCount;
    @ManyToOne private ModelItem cartItem;
}
