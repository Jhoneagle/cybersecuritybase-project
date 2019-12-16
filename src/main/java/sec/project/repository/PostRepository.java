package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
