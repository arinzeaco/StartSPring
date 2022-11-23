package com.example.StartSPring.rest;


//import com.example.StartSPring.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
public class ContactRestController {
//    @Autowired
//    ContactRepository contactRepository;


    @RequestMapping(value = "/getAllContact", method = GET)
    public ResponseEntity<Response> getcontact() {
//        Iterable<Contact> holidays = contactRepository.findAll();
        log.info("Header_invocationFrom", "holidays.toString()");

//        List<Contact> holidayList = StreamSupport
//                .stream(holidays.spliterator(), false)
//                .collect(Collectors.toList());
//
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
//        response.setData(holidayList);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

    @PostMapping("/postAllContact")
    public ResponseEntity<Response> postAllContact(
            @Valid @RequestBody Contact contact){
//        log.info("Header_invocationFrom", "holidays.hdc()");
        System.out.println("Exception while logging outgoing response");

//        log.info(String.format("Header invocationFrom = %s", invocationFrom));
//        contactRepository.save(contact);

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        response.setData(contact);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

}
