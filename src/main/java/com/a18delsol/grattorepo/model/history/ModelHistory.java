package com.a18delsol.grattorepo.model.history;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class ModelHistory {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer historyID;

    private String  historyText;
    private LocalDate historyDate;
    private LocalTime historyTime;
}
