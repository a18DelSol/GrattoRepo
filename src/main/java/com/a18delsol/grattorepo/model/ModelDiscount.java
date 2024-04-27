package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ModelDiscount {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer discountID;

    private String discountName;
    private LocalDate discountScheduleBegin;
    private LocalDate discountScheduleClose;
    private Float discountPercent;
}
