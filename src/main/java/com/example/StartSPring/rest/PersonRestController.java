package com.example.StartSPring.rest;


import com.example.StartSPring.model.Contact;
import com.example.StartSPring.model.Login;
import com.example.StartSPring.model.Person;
import com.example.StartSPring.repository.ContactRepository;
import com.example.StartSPring.repository.RolesRepository;
import com.example.StartSPring.model.Roles;
import com.example.StartSPring.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(path = "/api/person", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
public class PersonRestController {

    @Autowired
    PersonRepository personRepository;


    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ContactRepository contactRepository;


    @PostMapping(value = "/login")
    public ResponseEntity<Response> login(RequestEntity<Login> requestEntity){
        {
            HttpHeaders headers = requestEntity.getHeaders();
            headers.forEach((key, value) -> {
                log.info(String.format(
                        "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
            });

                Login login = requestEntity.getBody();
                Person person = personRepository.readByEmail(login.getEmail());

                if (null != person && person.getPersonId() > 0 &&
                        passwordEncoder.matches(login.getPassword(), person.getPwd())) {
                    Response response = new Response();
                    response.setStatusCode("200");
                    response.setStatusMsg("Message saved successfully");
                    response.setData(person);
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .header("isMsgSaved", "true")
                            .body(response);
                }
                throw new UserNotFoundException("id:"+login.getEmail());


//                Response response = new Response();
//                response.setStatusCode("400");
//                response.setStatusMsg("Login failed");
//
//                return ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .header("isMsgSaved", "true")
//                        .body(response);

        }
    }

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(EazySchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }
    @DeleteMapping(value = "/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity){
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq){
        Response response = new Response();
        Optional<Contact> contact = contactRepository.findById(contactReq.getContactId());
        if(contact.isPresent()){
            contact.get().setStatus(EazySchoolConstants.CLOSE);
            contactRepository.save(contact.get());
            response.setStatusCode("200");
            response.setStatusMsg("Message successfully closed");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
    }

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
