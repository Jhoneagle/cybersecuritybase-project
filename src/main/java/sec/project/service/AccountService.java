package sec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sec.project.domain.entities.Account;
import sec.project.domain.models.AdminsModel;
import sec.project.domain.models.UserValidator;
import sec.project.repository.AccountRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setUp() {
        if (this.accountRepository.findByUsername("admin") == null) {
            Account admin = new Account();
            admin.setUsername("admin");
            admin.setPassword("$2a$10$aBDNqdqZ2m9jHYxIX7Q8P.TN2z9cBNq8DSYd3xkVfdYSxgEvkQO46");
            admin.setFirstName("Never");
            admin.setLastName("Hacked");
            admin.getAuthorities().add("USER");
            admin.getAuthorities().add("ADMIN");

            this.accountRepository.save(admin);
        }
    }

    /**
     * Finds users account by his username.
     *
     * @param username username of account.
     *
     * @return account found.
     */
    public Account findByUsername(String username) {
        return this.accountRepository.findByUsername(username);
    }

    /**
     * Creates a new account and saves it into database according to the data gotten in the model.
     */
    public void create(UserValidator account) {
        Account validated = new Account();
        validated.setUsername(account.getUsername());
        validated.setPassword(passwordEncoder.encode(account.getPassword()));
        validated.setFirstName(account.getFirstName());
        validated.setLastName(account.getLastName());
        validated.getAuthorities().add("USER");

        this.accountRepository.save(validated);
    }

    public void updatePassword(String username, String password) {
        Account user = this.accountRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        this.accountRepository.save(user);
    }

    public void removeUser(String username) {
        Account user = this.accountRepository.findByUsername(username);
        this.accountRepository.delete(user);
    }

    public List<AdminsModel> getAllUsers() {
        return this.accountRepository.findAll().stream().map(t -> new AdminsModel(t.toString(), t.getUsername())).collect(Collectors.toList());
    }
}
