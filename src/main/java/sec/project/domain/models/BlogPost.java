package sec.project.domain.models;

import java.time.LocalDateTime;

public class BlogPost {
    private Long id;
    private String label;
    private String author;
    private Long likes;
    private LocalDateTime timestamp;

    public BlogPost(Long id, String label, String author, Long likes, LocalDateTime timestamp) {
        this.id = id;
        this.label = label;
        this.author = author;
        this.likes = likes;
        this.timestamp = timestamp;
    }

    public BlogPost() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
