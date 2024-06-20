package com.a18delsol.grattorepo.repository.contact;

import com.a18delsol.grattorepo.model.contact.ModelContact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryContact extends CrudRepository<ModelContact, Integer> {
    @Query(nativeQuery=true,
    value="SELECT * FROM model_contact"
    + " WHERE (:contactName is null OR contact_name regexp :contactName)"
    + " AND   (:contactMail is null OR contact_mail regexp :contactMail)"
    + " AND   (:contactCall is null OR contact_call regexp :contactCall)")
    Iterable<ModelContact> findContact(
    Optional<String> contactName,
    Optional<String> contactMail,
    Optional<String> contactCall);
}