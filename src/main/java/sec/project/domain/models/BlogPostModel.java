package sec.project.domain.models;

import java.time.LocalDateTime;

public class BlogPostModel extends BlogPost {
    private String content;
    private boolean likedAlready;

    public BlogPostModel(Long id, String label, String author, int likes, LocalDateTime timestamp, String content, boolean likedAlready) {
        super(id, label, author, likes, timestamp);
        this.content = content;
        this.likedAlready = likedAlready;
    }

    public BlogPostModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLikedAlready() {
        return likedAlready;
    }

    public void setLikedAlready(boolean likedAlready) {
        this.likedAlready = likedAlready;
    }
}
