package com.example.StartSPring.rest;


import com.example.StartSPring.repository.ContactRepository;
import com.example.StartSPring.repository.PersonRepository;
import com.example.StartSPring.repository.Response;
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

    @Autowired
    PersonRepository personRepository;
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

    
    @PostMapping(value = "/addPerson")
    public ResponseEntity<Response> addPerson(
            @Valid @RequestBody Person person){
//        log.info("Header_invocationFrom", "holidays.hdc()");
//        System.out.println("Exception while logging outgoing response");
//
////        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        Person savedPerson = personRepository.save(person);
        if(null != savedPerson) {

            Response response = new Response();
            response.setStatusCode("200");
            response.setStatusMsg("Message saved successfully");
            response.setData(person);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("isMsgSaved", "true")
                    .body(response);
        }
        Response response = new Response();
        response.setStatusCode("400");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }


}
