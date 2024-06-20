package com.a18delsol.grattorepo.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
public class ModelUser {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer userID;

    private String userName;
    private String userCall;
    private String userAddress;
    private String userMail;
    private String userPass;
    private LocalDate userBirth;
    @ManyToMany private Set<ModelUserAttribute> userAttribute;
}
