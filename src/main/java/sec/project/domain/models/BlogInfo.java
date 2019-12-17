package sec.project.domain.models;

import java.util.List;

public class BlogInfo {
    private String name;
    private int follows;
    private boolean canFollow;
    private List<BlogPost> posts;

    public BlogInfo(String name, int follows, boolean canFollow, List<BlogPost> posts) {
        this.name = name;
        this.follows = follows;
        this.canFollow = canFollow;
        this.posts = posts;
    }

    public BlogInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public boolean isCanFollow() {
        return canFollow;
    }

    public void setCanFollow(boolean canFollow) {
        this.canFollow = canFollow;
    }

    public List<BlogPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BlogPost> posts) {
        this.posts = posts;
    }
}
