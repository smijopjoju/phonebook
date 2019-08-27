package my.demo.phonebook.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import my.demo.phonebook.core.ContactDetailsService;
import my.demo.phonebook.domain.Contacts;
import my.demo.phonebook.exceptions.DownloadableFileCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@Api(tags = "Contacts detail API")
public class PhoneBookController {

    @Autowired
    ContactDetailsService service;

    @GetMapping("")
    @ApiOperation(value = "API to retrieve all contact details", response = List.class)
    public ResponseEntity getAllContact() {
        List<Contacts> contacts = service.getAllContacts();
        return new ResponseEntity(contacts,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API to retrieve contact details based on ID", response = Contacts.class)
    public ResponseEntity getContact(@PathVariable("id") Long Id) {
        Contacts contact = service.getContactById(Id);
        if(contact != null) {
            return new ResponseEntity(contact, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/contactname/{name}")
    @ApiOperation(value = "API to retrieve contact detail based on contact name", response = List.class)
    public ResponseEntity getContactByName(@PathVariable("name") String name) {
        List<Contacts> contacts = service.getContactByName(name);
        return new ResponseEntity(contacts,HttpStatus.OK);
    }

    @GetMapping("/phonenumber/{phonenumber}")
    @ApiOperation(value = "API to retrieve contact details based on contact phone number",response = List.class)
    public ResponseEntity getContactByPhoneNumber(@PathVariable("phonenumber") String phoneNumber) {
        List<Contacts> contacts = service.getContactByPhoneNumber(phoneNumber);
        return new ResponseEntity(contacts,HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "API to create new contact details to the system",response = Contacts.class)
    public ResponseEntity createNewContact(@RequestBody Contacts contacts) {
        Contacts newContact = service.createNewContact(contacts);
        return new ResponseEntity(newContact,HttpStatus.CREATED);
    }

    @PutMapping("")
    @ApiOperation(value = "API to update the existing contact detail",response = Contacts.class)
    public ResponseEntity updateContact(@RequestBody Contacts contacts) {
        Contacts updatedContact = service.updateContact(contacts);
        return new ResponseEntity(updatedContact,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "API to delete the existing contact detail from the system")
    public ResponseEntity deleteContact(@PathVariable("id") Long Id) {
        service.deleteContact(Id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadContactDetails(@RequestParam("file") MultipartFile file) {
        service.updateContactDetailsFromFile(file);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadContactDetails() {
        try {
            File downloadableFile = service.createDownloadableFileWithSelectedContent();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadableFile));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Content-Disposition", "attachment; filename=contacts.csv");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(downloadableFile.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (Exception e) {
            throw new DownloadableFileCreationException(e);
        }

    }
}
