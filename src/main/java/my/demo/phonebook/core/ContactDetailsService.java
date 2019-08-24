package my.demo.phonebook.core;

import my.demo.phonebook.db.ContactDetail;
import my.demo.phonebook.domain.Contacts;
import my.demo.phonebook.exceptions.ContentReadException;
import my.demo.phonebook.exceptions.CorreptedContactException;
import my.demo.phonebook.exceptions.EmptyFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ContactDetailsService {

    @Autowired
    FileHandlerService fileService;

    @Autowired
    ContactDetailsRepo repository;

    public Contacts createNewContact(final Contacts newContact) {
        ContactDetail newContactDetail = modelToDB(newContact);
        newContactDetail = repository.save(newContactDetail);
        Contacts savedContact = dbToModel(newContactDetail);
        return savedContact;
    }

    public Contacts updateContact(final Contacts contact) {
        ContactDetail contactDetail = modelToDB(contact);
        contactDetail = repository.save(contactDetail);
        Contacts updatedContact = dbToModel(contactDetail);
        return updatedContact;
    }

    public boolean deleteContact(final Contacts contact) {
        ContactDetail contactDetail = modelToDB(contact);
        repository.delete(contactDetail);
        return true;
    }

    public Contacts getContactById(final Long Id) {

        ContactDetail contactDetail = repository.getOne(Id);
        return dbToModel(contactDetail);
    }

    public List<Contacts> getContactByPhoneNumber(final String phoneNumber) {
        List<ContactDetail> contactDetails = repository.findByPersonalNumber(phoneNumber);
        return getContactsFromContactDetails(contactDetails);
    }

    public List<Contacts> getContactByName(final String contactName) {
        List<ContactDetail> contactDetails = repository.findByContactName(contactName);
        return getContactsFromContactDetails(contactDetails);
    }

    protected List<Contacts> getContactsFromContactDetails(List<ContactDetail> contactDetails) {
        List<Contacts> contacts = new ArrayList<>();
        for(ContactDetail contactDetail : contactDetails) {
            if(contactDetail != null) {
                contacts.add(dbToModel(contactDetail));
            }
        }
        return contacts;
    }

    public boolean updateContactDetailsFromFile(MultipartFile uploadedFile) {

        if(uploadedFile.isEmpty()) {
            throw new EmptyFileException("Uploaded file is empty, Please select a file");
        }

        return readFileContentAndSyncWithContacts(uploadedFile);
    }

    protected boolean readFileContentAndSyncWithContacts(MultipartFile file) {
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

    public ContactDetail modelToDB(Contacts contact) {
        if(contact != null) {
            ContactDetail contactDetail = new ContactDetail();
            contactDetail.setPersonalNumber(contact.getPersonalNumber());
            contactDetail.setOfficeNumber(contact.getOfficeNumber());
            contactDetail.setHomeNumber(contact.getHomeNumber());
            contactDetail.setContactName(contact.getContactName());
            contactDetail.setEmailId(contact.getEmailId());
            return contactDetail;
        } else {
            throw new CorreptedContactException(" Contact detail is null "+contact);
        }
    }

    public Contacts dbToModel(ContactDetail contactDetail) {
        if(contactDetail != null) {
            Contacts contacts = new Contacts();
            contacts.setContactName(contactDetail.getContactName());
            contacts.setEmailId(contactDetail.getEmailId());
            contacts.setHomeNumber(contactDetail.getHomeNumber());
            contacts.setOfficeNumber(contactDetail.getOfficeNumber());
            contacts.setPersonalNumber(contactDetail.getPersonalNumber());
            return contacts;
        } else {
            throw new CorreptedContactException(" Contact details model class is null "+contactDetail);
        }
    }
}
