package com.a18delsol.grattorepo.model.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModelContact {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer contactID;

    @NotBlank private String contactName;
    private String contactMail;
    private String contactCall;
    @NotNull private Boolean contactProvider;
    //@ManyToMany private Set<ModelItem>        contactItem;
    //@ManyToMany private Set<ModelItemCompany> contactCompany;
}