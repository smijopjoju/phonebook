package my.demo.phonebook.core;

import my.demo.phonebook.db.ContactDetail;
import my.demo.phonebook.exceptions.ContentReadException;
import my.demo.phonebook.exceptions.EmptyFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ContactDetailsService {

    @Autowired
    FileHandlerService fileService;

    public boolean updateContactDetailsFromFile(MultipartFile uploadedFile) {

        if(uploadedFile.isEmpty()) {
            throw new EmptyFileException("Uploaded file is empty, Please select a file");
        }

        return readFileContentAndSyncWithContacts(uploadedFile);
    }

    public boolean readFileContentAndSyncWithContacts(MultipartFile file) {
        try {
                List<ContactDetail> uploadedContactDetails = fileService.getAllContactDetailsFromFile(file);
                return syncContactDetails(uploadedContactDetails);
        } catch (Exception e) {
            throw new ContentReadException(e);
        }
    }

    public boolean syncContactDetails(List<ContactDetail> contacts) {
        return false;
    }

}
