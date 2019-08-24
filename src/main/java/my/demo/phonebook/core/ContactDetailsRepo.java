package my.demo.phonebook.core;

import my.demo.phonebook.db.ContactDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDetailsRepo extends JpaRepository<ContactDetail,Long> {


}
