package sec.project.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;;

@Entity
public class Account extends AbstractPersistable<Long> {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    public Account(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
        this.authorities = new ArrayList<>();
        this.follows = new HashSet<>();
    }

    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    /**
     * Simplified way to get users full name without having it as one field still.
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @OneToMany(mappedBy = "creator")
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Users_follows",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id", referencedColumnName = "id"))
    private Set<Account> follows;

    public Set<Account> getFollows() {
        return follows;
    }

    public void setFollows(Set<Account> follows) {
        this.follows = follows;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
