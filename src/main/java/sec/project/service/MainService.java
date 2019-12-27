package sec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sec.project.domain.entities.Account;
import sec.project.domain.entities.Likes;
import sec.project.domain.entities.Post;
import sec.project.domain.models.*;
import sec.project.repository.AccountRepository;
import sec.project.repository.LikesRepository;
import sec.project.repository.PostRepository;

import java.time.LocalDateTime;
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

    @Autowired
    private LikesRepository likesRepository;

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
        Pageable pageable = PageRequest.of(0, 50, Sort.by("timestamp").descending());
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

    public BlogPostModel getFullPost(Long postId) {
        String loggedInPerson = SecurityContextHolder.getContext().getAuthentication().getName();
        Account byUsername = this.accountRepository.findByUsername(loggedInPerson);

        Post post = this.postRepository.getOne(postId);

        BlogPostModel result = new BlogPostModel();

        result.setAuthor(post.getCreator().toString());
        result.setId(post.getId());
        result.setLabel(post.getLabel());
        result.setTimestamp(post.getTimestamp());
        result.setContent(post.getContent());

        List<Likes> likes = post.getLikes();
        result.setLikes(likes.size());

        likes.stream().filter(like -> like.getUser().getUsername().equals(byUsername.getUsername())).map(like -> true).forEach(result::setLikedAlready);
        result.setOwnsPost(post.getCreator().getUsername().equals(byUsername.getUsername()));

        return result;
    }

    public void removePost(Long postId) {
        Post post = this.postRepository.getOne(postId);
        this.likesRepository.deleteAllByLikedPost(post);
        this.postRepository.delete(post);
    }

    public void likePost(Long postId) {
        String loggedInPerson = SecurityContextHolder.getContext().getAuthentication().getName();
        Account byUsername = this.accountRepository.findByUsername(loggedInPerson);

        Post post = this.postRepository.getOne(postId);

        Likes like = new Likes(byUsername, post);
        this.likesRepository.save(like);
    }

    public void unlikePost(Long postId) {
        Post post = this.postRepository.getOne(postId);

        String loggedInPerson = SecurityContextHolder.getContext().getAuthentication().getName();
        Account byUsername = this.accountRepository.findByUsername(loggedInPerson);

        Likes like = this.likesRepository.findByUserAndLikedPost(byUsername, post);
        this.likesRepository.delete(like);
    }

    public void createPost(PostValidator postValidator) {
        String loggedInPerson = SecurityContextHolder.getContext().getAuthentication().getName();
        Account byUsername = this.accountRepository.findByUsername(loggedInPerson);

        Post post = new Post();
        post.setContent(postValidator.getContent());
        post.setCreator(byUsername);
        post.setLabel(postValidator.getLabel());
        post.setTimestamp(LocalDateTime.now());

        this.postRepository.save(post);
    }

    public List<BlogPost> getAllPosts() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by("timestamp").descending());
        Page<Post> posts = this.postRepository.findAll(pageable);

        return getBlogPosts(posts.getContent());
    }

    public Long getCreatorId(Long postId) {
        return this.postRepository.getOne(postId).getCreator().getId();
    }

    public boolean isOwnerOfBlog(Long id) {
        String loggedInPerson = SecurityContextHolder.getContext().getAuthentication().getName();
        Account byUsername = this.accountRepository.findByUsername(loggedInPerson);

        return id.equals(byUsername.getId());
    }

    @Transactional
    public void followPerson(Long id) {
        String loggedInPerson = SecurityContextHolder.getContext().getAuthentication().getName();
        Account byUsername = this.accountRepository.findByUsername(loggedInPerson);

        Account one = this.accountRepository.getOne(id);
        byUsername.getFollows().add(one);
        one.getFollows().add(byUsername);
    }

    @Transactional
    public void unFollowPerson(Long id) {
        String loggedInPerson = SecurityContextHolder.getContext().getAuthentication().getName();
        Account byUsername = this.accountRepository.findByUsername(loggedInPerson);

        Account one = this.accountRepository.getOne(id);
        byUsername.getFollows().remove(one);
        one.getFollows().remove(byUsername);
    }
}
