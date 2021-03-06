package sec.project.domain.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.*;
;

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
        this.liked = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.authorities = new ArrayList<>();
        this.follows = new HashSet<>();
        this.followers = new HashSet<>();
    }

    public Account() {
        this.liked = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.authorities = new ArrayList<>();
        this.follows = new HashSet<>();
        this.followers = new HashSet<>();
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "followers")
    private Set<Account> follows;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Account> followers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Likes> liked;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Likes> getLiked() {
        return liked;
    }

    public void setLiked(List<Likes> liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(getUsername(), account.getUsername()) &&
                Objects.equals(getFirstName(), account.getFirstName()) &&
                Objects.equals(getLastName(), account.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUsername(), getFirstName(), getLastName());
    }

    public Set<Account> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Account> followers) {
        this.followers = followers;
    }

    public Set<Account> getFollows() {
        return follows;
    }

    public void setFollows(Set<Account> follows) {
        this.follows = follows;
    }

    @PreRemove
    public void handleAssociations() {
        this.followers.forEach(t -> t.getFollows().remove(this));

        this.follows.forEach(t -> t.getFollowers().remove(this));
    }
}
