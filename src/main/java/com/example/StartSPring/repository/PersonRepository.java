package com.example.StartSPring.repository;

import com.example.StartSPring.rest.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

//    Person readByEmail(String email);

}
