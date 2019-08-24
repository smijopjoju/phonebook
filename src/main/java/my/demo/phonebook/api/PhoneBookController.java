package my.demo.phonebook.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/contacts")
@Api(tags = "Contacts detail API")
public class PhoneBookController {

    @GetMapping("/{id}")
    @ApiOperation(value = "API to retrieve contact details based on ID")
    public ResponseEntity getContact(@PathVariable("id") Long Id) {
        return null;
    }

    @GetMapping("/{name}")
    @ApiOperation(value = "API to retrieve contact detail based on contact name")
    public ResponseEntity getContactByName(@PathVariable("name") String name) {
        return null;
    }

    @GetMapping("/{phonenumber}")
    @ApiOperation(value = "API to retrieve contact details based on contact phone number")
    public ResponseEntity getContactByPhoneNumber(@PathVariable("phonenumber") String phoneNumber) {
        return null;
    }

    @PostMapping("/")
    @ApiOperation(value = "API to create new contact details to the system")
    public ResponseEntity createNewContact() {
        return null;
    }

    @PutMapping("/")
    @ApiOperation(value = "API to update the existing contact detail")
    public ResponseEntity updateContact() {
        return null;
    }

    @DeleteMapping("/")
    @ApiOperation(value = "API to delete the existing contact detail from the system")
    public ResponseEntity deleteContact() {
        return null;
    }

    @PostMapping("/upload")
    public ResponseEntity uploadContactDetails(@RequestParam("file") MultipartFile file) {
        return null;
    }
}
