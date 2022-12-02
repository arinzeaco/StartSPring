package com.example.StartSPring.rest;


import com.example.StartSPring.model.Contact;
import com.example.StartSPring.model.Person;
import com.example.StartSPring.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@RestController
@RequestMapping(path = "/api/contact",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins="*")
public class ContactRestController {
    @Autowired
    ContactRepository contactRepository;

//    @Autowired
//    PersonRepository personRepository;
    @GetMapping(value = "/getAllContact")
    public ResponseEntity<Response> getcontact() {
        Iterable<Contact> holidays = contactRepository.findAll();

        List<Contact> holidayList = StreamSupport
                .stream(holidays.spliterator(), false)
                .collect(Collectors.toList());
//
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        response.setData(holidayList);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }



}
