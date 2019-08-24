package my.demo.phonebook.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="phonebook")
public class ContactDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contactName;

    private String personalNumber;

    private String officeNumber;

    private String homeNumber;

    private String emailId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetail contactDetail = (ContactDetail) o;
        return Objects.equals(id, contactDetail.id) &&
                Objects.equals(contactName, contactDetail.contactName) &&
                Objects.equals(personalNumber, contactDetail.personalNumber) &&
                Objects.equals(officeNumber, contactDetail.officeNumber) &&
                Objects.equals(homeNumber, contactDetail.homeNumber) &&
                Objects.equals(emailId, contactDetail.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactName, personalNumber, officeNumber, homeNumber, emailId);
    }

    @Override
    public String toString() {
        return "PhoneBook{" +
                "id=" + id +
                ", contactName='" + contactName + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", officeNumber='" + officeNumber + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
