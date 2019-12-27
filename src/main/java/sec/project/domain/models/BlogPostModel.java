package sec.project.domain.models;

import java.time.LocalDateTime;

public class BlogPostModel extends BlogPost {
    private String content;
    private boolean likedAlready;
    private boolean ownsPost;

    public BlogPostModel(Long id, String label, String author, int likes, LocalDateTime timestamp, String content, boolean likedAlready, boolean ownsPost) {
        super(id, label, author, likes, timestamp);
        this.content = content;
        this.likedAlready = likedAlready;
        this.ownsPost = ownsPost;
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

    public boolean isOwnsPost() {
        return ownsPost;
    }

    public void setOwnsPost(boolean ownsPost) {
        this.ownsPost = ownsPost;
    }
}
