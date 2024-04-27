package com.a18delsol.grattorepo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
public class ModelUser {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer userID;

    private String userName;
    private String userMail;
    private String userPass;
    private LocalDate userBirth;
    @ManyToMany
    private Set<ModelAttribute> userAttribute;
}
