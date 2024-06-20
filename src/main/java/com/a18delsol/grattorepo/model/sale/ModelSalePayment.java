package com.a18delsol.grattorepo.model.sale;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelSalePayment {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer paymentID;

    private String paymentName;
}
