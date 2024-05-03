package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
public class ModelDiscount {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer discountID;

    private String discountName;
    private LocalDate discountScheduleBegin;
    private LocalDate discountScheduleClose;
    private Float discountPercent;
    @ManyToMany @JoinTable(name = "discount_user_attribute_join") private Set<ModelUserAttribute> discountUserAttribute;
    @ManyToMany @JoinTable(name = "discount_item_attribute_join") private Set<ModelItemAttribute> discountItemAttribute;
    @ManyToMany @JoinTable(name = "discount_item_company_join") private Set<ModelItemCompany> discountItemCompany;
}
