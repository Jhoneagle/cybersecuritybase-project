package sec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sec.project.domain.entities.Account;
import sec.project.domain.models.BlogPost;
import sec.project.domain.models.UserModel;
import sec.project.repository.AccountRepository;
import sec.project.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    public List<UserModel> findPeopleWithParam(String search) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Parse the parameter in the actual parameter and get the result as raw data from database.
        String[] parts = search.split(" ");
        List<Account> users;
        if (parts.length > 1) {
            StringBuilder firstName = new StringBuilder(parts[0]);
            String lastName = parts[parts.length - 1];

            for (int i = 1; i < parts.length - 1; i++) {
                firstName.append(" ").append(parts[i]);
            }

            users = this.accountRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName.toString(), lastName);
        } else {
            String name = parts[0];

            users = this.accountRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
        }

        return users.stream().map(user -> new UserModel(user.toString(), user.getId())).collect(Collectors.toList());
    }

    public List<BlogPost> findPostsWithParam(String searchField) {
        return null;
    }
}
