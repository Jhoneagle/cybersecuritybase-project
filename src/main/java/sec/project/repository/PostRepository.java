package sec.project.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.entities.Account;
import sec.project.domain.entities.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByLabelContainingIgnoreCase(String label, Pageable pageable);
    List<Post> findAllByCreator(Account creator, Pageable pageable);
}
