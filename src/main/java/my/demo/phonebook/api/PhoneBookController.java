package my.demo.phonebook.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import my.demo.phonebook.core.ContactDetailsService;
import my.demo.phonebook.domain.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@Api(tags = "Contacts detail API")
public class PhoneBookController {

    @Autowired
    ContactDetailsService service;

    @GetMapping("/{id}")
    @ApiOperation(value = "API to retrieve contact details based on ID", response = Contacts.class)
    public ResponseEntity getContact(@PathVariable("id") Long Id) {
        Contacts contact = service.getContactById(Id);
        return new ResponseEntity(contact, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    @ApiOperation(value = "API to retrieve contact detail based on contact name", response = List.class)
    public ResponseEntity getContactByName(@PathVariable("name") String name) {
        List<Contacts> contacts = service.getContactByName(name);
        return new ResponseEntity(contacts,HttpStatus.OK);
    }

    @GetMapping("/{phonenumber}")
    @ApiOperation(value = "API to retrieve contact details based on contact phone number",response = List.class)
    public ResponseEntity getContactByPhoneNumber(@PathVariable("phonenumber") String phoneNumber) {
        List<Contacts> contacts = service.getContactByPhoneNumber(phoneNumber);
        return new ResponseEntity(contacts,HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation(value = "API to create new contact details to the system",response = Contacts.class)
    public ResponseEntity createNewContact(@RequestBody Contacts contacts) {
        Contacts newContact = service.createNewContact(contacts);
        return new ResponseEntity(newContact,HttpStatus.CREATED);
    }

    @PutMapping("/")
    @ApiOperation(value = "API to update the existing contact detail",response = Contacts.class)
    public ResponseEntity updateContact(@RequestBody Contacts contacts) {
        Contacts updatedContact = service.updateContact(contacts);
        return new ResponseEntity(updatedContact,HttpStatus.OK);
    }

    @DeleteMapping("/")
    @ApiOperation(value = "API to delete the existing contact detail from the system")
    public ResponseEntity deleteContact(Contacts contacts) {
        service.deleteContact(contacts);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadContactDetails(@RequestParam("file") MultipartFile file) {
        service.updateContactDetailsFromFile(file);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/download")
    public ResponseEntity downloadContactDetails(@RequestBody List<Contacts> contacts) {
        return null;
    }
}
