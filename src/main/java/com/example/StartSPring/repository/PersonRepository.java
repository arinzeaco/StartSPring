package com.example.StartSPring.repository;

import com.example.StartSPring.model.Person;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {


    Person findByEmailAndPwd(String email, String password);


}
