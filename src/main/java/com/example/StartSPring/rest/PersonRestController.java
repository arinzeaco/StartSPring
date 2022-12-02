package com.example.StartSPring.rest;


import com.example.StartSPring.model.Login;
import com.example.StartSPring.model.Person;
import com.example.StartSPring.rest.RolesRepository;
import com.example.StartSPring.model.Roles;
import com.example.StartSPring.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(path = "/api/person", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
public class PersonRestController {

    @Autowired
    PersonRepository personRepository;


//    @Autowired
//    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/login")
    public ResponseEntity<Response> login(
            @Valid @RequestBody Login login
    ) {
        try {
            Person savedPerson = personRepository.findByEmailAndPwd(login.getEmail(), login.getPassword());
            if (null != savedPerson) {

                Response response = new Response();
                response.setStatusCode("200");
                response.setStatusMsg("Message saved successfully");
                response.setData(savedPerson);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .header("isMsgSaved", "true")
                        .body(response);
            }

            Response response = new Response();
            response.setStatusCode("400");
            response.setStatusMsg("Login failed");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("isMsgSaved", "true")
                    .body(response);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Actor Not Found");
        }

    }

//    public boolean createNewPerson(Person person){
//        boolean isSaved = false;
//        Roles role = rolesRepository.getByRoleName(EazySchoolConstants.STUDENT_ROLE);
//        person.setRoles(role);
//        person.setPwd(passwordEncoder.encode(person.getPwd()));
//        person = personRepository.save(person);
//        if (null != person && person.getPersonId() > 0)
//        {
//            isSaved = true;
//        }
//        return isSaved;
//    }


//    @PostMapping(value = "/register")
//    public ResponseEntity<Response> register(
//            @Valid @RequestBody Person person) {
//        boolean isSaved = false;
//        Roles role = rolesRepository.getByRoleName(EazySchoolConstants.STUDENT_ROLE);
//        person.setRoles(role);
//        person.setPwd(passwordEncoder.encode(person.getPwd()));
//        person = personRepository.save(person);
//        if (null != person && person.getPersonId() > 0) {
//            isSaved = true;
//        }
//
//        Person savedPerson = personRepository.save(person);
//        if (null != savedPerson) {
//
//            Response response = new Response();
//            response.setStatusCode("200");
//            response.setStatusMsg("Message saved successfully");
//            response.setData(person);
//            return ResponseEntity
//                    .status(HttpStatus.CREATED)
//                    .header("isMsgSaved", "true")
//                    .body(response);
//        }
//        Response response = new Response();
//        response.setStatusCode("400");
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .header("isMsgSaved", "true")
//                .body(response);
//    }

    public interface EazySchoolConstants {

        public static final String OPEN = "Open";
        public static final String CLOSE = "Close";
        public static final String STUDENT_ROLE = "STUDENT";
        public static final String ADMIN_ROLE = "ADMIN";
    }


}
