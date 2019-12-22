package sec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sec.project.domain.entities.Account;
import sec.project.domain.models.UserValidator;
import sec.project.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public void updatePassword(Long userId, String password) {
        Account user = this.accountRepository.getOne(userId);
        user.setPassword(passwordEncoder.encode(password));
        this.accountRepository.save(user);
    }
}
