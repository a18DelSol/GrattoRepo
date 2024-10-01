package com.a18delsol.grattorepo.model.contact;

import com.a18delsol.grattorepo.model.item.ModelItem;
import com.a18delsol.grattorepo.model.item.ModelItemCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelContact {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer ID;

    @NotBlank            private String  contactName;
    @NotNull             private String  contactMail;
    @NotNull             private String  contactCall;
    @NotNull             private String  contactLocation;
    @NotNull             private String  contactService;
    @NotNull             private Boolean contactProvider;
    @NotNull @ManyToMany private Set<ModelItem> contactItem;
    @NotNull @ManyToMany private Set<ModelItemCompany> contactCompany;
}