package sec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sec.project.domain.entities.Account;
import sec.project.domain.entities.Post;
import sec.project.domain.models.BlogInfo;
import sec.project.domain.models.BlogPost;
import sec.project.domain.models.BlogPostModel;
import sec.project.domain.models.UserModel;
import sec.project.repository.AccountRepository;
import sec.project.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MainService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    public List<UserModel> findPeopleWithParam(String search) {
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
        Pageable pageable = PageRequest.of(0, 25, Sort.by("timestamp").descending());
        List<Post> posts = this.postRepository.findAllByLabelContainingIgnoreCase(searchField, pageable);

        return getBlogPosts(posts);
    }

    public List<BlogPost> getUsersRecentPosts(Account user) {
        Pageable pageable = PageRequest.of(0, 25, Sort.by("timestamp").descending());
        List<Post> posts = this.postRepository.findAllByCreator(user, pageable);

        return getBlogPosts(posts);
    }

    private List<BlogPost> getBlogPosts(List<Post> posts) {
        List<BlogPost> models = new ArrayList<>();

        posts.forEach(post -> {
            BlogPost t = new BlogPost();
            t.setAuthor(post.getCreator().toString());
            t.setId(post.getId());
            t.setLabel(post.getLabel());
            t.setTimestamp(post.getTimestamp());
            t.setLikes(post.getLikes().size());

            models.add(t);
        });

        return models;
    }

    private Account getUser(Long id) {
        return this.accountRepository.getOne(id);
    }

    public BlogInfo getBlogDetails(Long id) {
        String loggedInPerson = SecurityContextHolder.getContext().getAuthentication().getName();
        Account byUsername = this.accountRepository.findByUsername(loggedInPerson);

        Account user = getUser(id);
        List<BlogPost> posts = getUsersRecentPosts(user);

        String name = user.toString();
        Set<Account> follows = user.getFollows();
        int amountFollows = follows.size();
        boolean canFollow;

        if (byUsername.getUsername().equals(user.getUsername())) {
            canFollow = false;
        } else {
            canFollow = follows.stream().noneMatch(t -> byUsername.getUsername().equals(t.getUsername()));
        }

        return new BlogInfo(name, amountFollows, canFollow, posts);
    }

    public BlogPostModel getFullPost(Long accountId, Long postId) {
        return null;
    }
}