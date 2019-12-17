package sec.project.domain.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Likes extends AbstractPersistable<Long> {
    @ManyToOne
    private Account user;

    @ManyToOne
    private Post likedPost;

    public Likes(Account user, Post likedPost) {
        this.user = user;
        this.likedPost = likedPost;
    }

    public Likes() {
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Post getLikedPost() {
        return likedPost;
    }

    public void setLikedPost(Post likedPost) {
        this.likedPost = likedPost;
    }
}
