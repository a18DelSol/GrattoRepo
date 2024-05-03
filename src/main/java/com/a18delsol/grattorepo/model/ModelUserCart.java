package com.a18delsol.grattorepo.model;

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
