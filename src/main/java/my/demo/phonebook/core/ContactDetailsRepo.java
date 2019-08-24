package my.demo.phonebook.core;

import my.demo.phonebook.db.ContactDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDetailsRepo extends JpaRepository<ContactDetail,Long> {

    public List<ContactDetail> findByPersonalNumber(String personalNumber);

    public List<ContactDetail> findByContactName(String contactName);

}
