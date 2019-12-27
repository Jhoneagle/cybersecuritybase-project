package sec.project.domain.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends AbstractPersistable<Long> {
    private String label;
    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    private Account creator;

    public Post() {
        this.likes = new ArrayList<>();
    }

    public Post(String label, String content, LocalDateTime timestamp, Account creator) {
        this.label = label;
        this.content = content;
        this.timestamp = timestamp;
        this.creator = creator;
        this.likes = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return content;
    }

    @OneToMany(mappedBy = "likedPost")
    private List<Likes> likes;

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }
}
