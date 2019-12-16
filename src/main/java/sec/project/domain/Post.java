package sec.project.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Post extends AbstractPersistable<Long> {
    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    private Account creator;

    public Post() {
    }

    public Post(String content, LocalDateTime timestamp, Account creator) {
        this.content = content;
        this.timestamp = timestamp;
        this.creator = creator;
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
}
