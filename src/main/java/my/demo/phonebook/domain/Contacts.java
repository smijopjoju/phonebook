package my.demo.phonebook.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contact details")
public class Contacts {

    @ApiModelProperty(name = "Id", dataType = "Number", required = false, value = "Contact identification number")
    private Long id;

    @ApiModelProperty(name = "Contact Name", dataType = "String", required = true, value = "Contact person name")
    private String contactName;

    @ApiModelProperty(name = "Personal Phone Number", dataType = "Number", required = true, value = "Personal contact number")
    private String personalNumber;

    @ApiModelProperty(name = "Office Phone Number", dataType = "Number", required = false, value = "Office contact number")
    private String officeNumber;

    @ApiModelProperty(name = "Home Phone Number", dataType = "Number", required = false, value = "Land phone number")
    private String homeNumber;

    @ApiModelProperty(name = "Email Address", dataType = "String", required = false, value = "Email Address")
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
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", contactName='" + contactName + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", officeNumber='" + officeNumber + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
