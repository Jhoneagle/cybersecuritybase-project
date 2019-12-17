package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.entities.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
