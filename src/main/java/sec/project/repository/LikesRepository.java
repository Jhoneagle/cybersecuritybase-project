package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.entities.Account;
import sec.project.domain.entities.Likes;
import sec.project.domain.entities.Post;

import java.util.Collection;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByUserAndLikedPost(Account user, Post likedPost);
    void deleteAllByLikedPost(Post likedPost);
    void deleteAllByUser(Account user);
    void deleteAllByLikedPostIn(Collection<Post> likedPost);
}
