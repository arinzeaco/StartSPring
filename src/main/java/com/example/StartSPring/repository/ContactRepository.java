package com.example.StartSPring.repository;

import com.example.StartSPring.model.Contact;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

//    Contact getByRoleName(String roleName);

}
