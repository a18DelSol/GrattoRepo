package com.a18delsol.grattorepo.model.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelStock {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer ID;

    @JsonIgnore private Boolean entityDelete = false;

    @NotBlank private String stockName;

    @OneToMany(mappedBy = "entryStock") @JsonIgnoreProperties("entryStock")
    private Set<ModelStockEntry> stockEntry;
}