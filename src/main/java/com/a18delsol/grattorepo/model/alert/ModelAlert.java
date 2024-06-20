package com.a18delsol.grattorepo.model.alert;

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
public class ModelAlert {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer alertID;

    private String  alertText;
    private LocalDate alertDate;
    private LocalTime alertTime;
    private Boolean alertSeen;
}
