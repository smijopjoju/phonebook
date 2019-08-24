package my.demo.phonebook.core;

import my.demo.phonebook.db.ContactDetail;
import my.demo.phonebook.exceptions.ContentReadException;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FileHandlerService {

    public List<ContactDetail> getAllContactDetailsFromFile(MultipartFile file) throws IOException {

        BufferedReader contentReader = getContentReaderForTheFile(file);
        return getAllContact(contentReader);
    }

    protected BufferedReader getContentReaderForTheFile(MultipartFile file) {
        try {
            byte[] fileContents = file.getBytes();
            File newFile = new File("newFile");
            FileUtils.writeByteArrayToFile(newFile,fileContents);
            return new BufferedReader(new FileReader(newFile));
        } catch (Exception e) {
            throw new ContentReadException("End up in exception while trying to retrieve content from upload file",e);
        }
    }

    protected List<ContactDetail> getAllContact(BufferedReader contentReader) throws IOException {
            List<ContactDetail> contactDetails = new ArrayList<>();
            String singleContact = null;
            while((singleContact=contentReader.readLine()) != null) {
                if(singleContact != null && !singleContact.isEmpty()) {
                    ContactDetail contact = createContactFromString(singleContact);
                    if(contact != null) {
                        contactDetails.add(contact);
                    }
                } else {

                }

            }

            return contactDetails;
    }

    protected ContactDetail createContactFromString(String singleContact) {
        String[] contactDetailsProperties = singleContact.split(",");
        if(contactDetailsProperties != null && contactDetailsProperties.length > 0) {
            ContactDetail contact = new ContactDetail();
            for(int index = 0; index < contactDetailsProperties.length; index++){
                    ContactProperties contactProperty = ContactProperties.getContactProp(index);
                    if(contactProperty != null) {
                        contact = updateContactProperties(contactProperty,contactDetailsProperties[index],contact);
                    }
            }
            return contact;
        }
        return null;
    }

    protected ContactDetail updateContactProperties(ContactProperties propIndex, String content, ContactDetail contact) {

        switch (propIndex) {
            case EMAIL_ID:
                    contact.setEmailId(content);
                    break;
            case CONTACT_NAME:
                    contact.setContactName(content);
                    break;
            case HOME_PHONE_NUMBER:
                    contact.setHomeNumber(content);
                    break;
            case OFFICE_PHONE_NUMBER:
                    contact.setOfficeNumber(content);
                    break;
            case PERSONAL_PHONE_NUMBER:
                    contact.setPersonalNumber(content);
                    break;
            default:
                    break;

        }

        return contact;
    }

    public File createDownloadableFile(StringBuilder content) throws IOException {
        File downloadableFile = new File("tempFile");
        FileUtils.writeByteArrayToFile(downloadableFile,content.toString().getBytes());
        return downloadableFile;
    }
}
