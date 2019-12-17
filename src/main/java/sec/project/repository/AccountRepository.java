package sec.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.entities.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    List<Account> findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
