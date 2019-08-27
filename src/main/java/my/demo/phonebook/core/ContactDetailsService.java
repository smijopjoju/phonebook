package my.demo.phonebook.core;

import my.demo.phonebook.db.ContactDetail;
import my.demo.phonebook.domain.Contacts;
import my.demo.phonebook.exceptions.ContentReadException;
import my.demo.phonebook.exceptions.CorreptedContactException;
import my.demo.phonebook.exceptions.DownloadableFileCreationException;
import my.demo.phonebook.exceptions.EmptyFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactDetailsService {

    @Autowired
    FileHandlerService fileService;

    @Autowired
    ContactDetailsRepo repository;

    private static final String CONTENT_SPLITTER = ",";
    private static final String NEW_LINE = "\n";

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

    public boolean deleteContact(final Long Id) {
        ContactDetail contactDetail = repository.getOne(Id);
        repository.delete(contactDetail);
        return true;
    }

    public List<Contacts> getAllContacts() {
        List<ContactDetail> contactDetails = repository.findAll();
        return getContactsFromContactDetails(contactDetails);
    }

    public Contacts getContactById(final Long Id) {

        Optional<ContactDetail> contactDetail = repository.findById(Id);
        if(contactDetail.isPresent()) {
            return dbToModel(contactDetail.get());
        }
        return null;
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
        for(ContactDetail contactDetail : contacts) {
            if(contactDetail != null && contactDetail.getPersonalNumber() != null) {
                List<ContactDetail> existingContacts = repository.findByPersonalNumber(contactDetail.getPersonalNumber());
                if(existingContacts != null && !existingContacts.isEmpty()) {
                    ContactDetail updatedContact = updateRecordAndSave(contactDetail,existingContacts.get(0));
                    repository.save(updatedContact);
                } else {
                    repository.save(contactDetail);
                }
            }

        }
        return true;
    }

    public ContactDetail updateRecordAndSave(ContactDetail newContactDetail, ContactDetail existingContactDetail) {

        existingContactDetail.setPersonalNumber(updateValue(existingContactDetail.getPersonalNumber(),newContactDetail.getPersonalNumber()));
        existingContactDetail.setContactName(updateValue(existingContactDetail.getContactName(),newContactDetail.getContactName()));
        existingContactDetail.setEmailId(updateValue(existingContactDetail.getEmailId(),newContactDetail.getEmailId()));
        existingContactDetail.setHomeNumber(updateValue(existingContactDetail.getHomeNumber(),newContactDetail.getHomeNumber()));
        existingContactDetail.setOfficeNumber(updateValue(existingContactDetail.getOfficeNumber(),newContactDetail.getOfficeNumber()));

        return existingContactDetail;
    }

    private String updateValue(String existingValue, String newValue) {
        String value = null;
        if(existingValue == null) {
            value = newValue;
        } else if(newValue != null && !existingValue.equalsIgnoreCase(newValue)){
            value = newValue;
        } else {
            value = existingValue;
        }
        return value;
    }

    protected ContactDetail modelToDB(Contacts contact) {
        if(contact != null) {
            ContactDetail contactDetail = new ContactDetail();
            contactDetail.setPersonalNumber(contact.getPersonalNumber());
            contactDetail.setOfficeNumber(contact.getOfficeNumber());
            contactDetail.setHomeNumber(contact.getHomeNumber());
            contactDetail.setContactName(contact.getContactName());
            contactDetail.setEmailId(contact.getEmailId());
            contactDetail.setId(contact.getId());
            return contactDetail;
        } else {
            throw new CorreptedContactException(" Contact detail is null "+contact);
        }
    }

    protected Contacts dbToModel(ContactDetail contactDetail) {
        if(contactDetail != null) {
            Contacts contacts = new Contacts();
            contacts.setContactName(contactDetail.getContactName());
            contacts.setEmailId(contactDetail.getEmailId());
            contacts.setHomeNumber(contactDetail.getHomeNumber());
            contacts.setOfficeNumber(contactDetail.getOfficeNumber());
            contacts.setPersonalNumber(contactDetail.getPersonalNumber());
            contacts.setId(contactDetail.getId());
            return contacts;
        } else {
            throw new CorreptedContactException(" Contact details model class is null "+contactDetail);
        }
    }

    public File createDownloadableFileWithSelectedContent() {
        try {
            List<ContactDetail> contacts = repository.findAll();
            StringBuilder content = getContentToTheDownloadableFile(contacts);
            return fileService.createDownloadableFile(content);
        } catch (Exception e) {
            throw new DownloadableFileCreationException("Something went wrong while trying to create download file",e);
        }

    }

    protected StringBuilder getContentToTheDownloadableFile(List<ContactDetail> contacts) {
        StringBuilder content = new StringBuilder();

        for(ContactDetail contact: contacts) {
                content.append(convertContactDetailToFileContent(contact));
        }

        return content;
    }

    protected String convertContactDetailToFileContent(ContactDetail contactDetail) {
        StringBuilder content = new StringBuilder();
        content.append(contactDetail.getContactName())
                .append(CONTENT_SPLITTER)
                .append(contactDetail.getPersonalNumber())
                .append(CONTENT_SPLITTER)
                .append(contactDetail.getOfficeNumber())
                .append(CONTENT_SPLITTER)
                .append(contactDetail.getHomeNumber())
                .append(CONTENT_SPLITTER)
                .append(contactDetail.getEmailId())
                .append(NEW_LINE);
        return content.toString();
    }
}
