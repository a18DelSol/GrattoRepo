package com.a18delsol.grattorepo.model.contact;

import com.a18delsol.grattorepo.model.item.ModelItemCompany;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ModelContact {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) private Integer contactID;

    private String contactName;
    private String contactMail;
    private String contactCall;
    @ManyToMany private Set<ModelItemCompany> contactCompany;
}