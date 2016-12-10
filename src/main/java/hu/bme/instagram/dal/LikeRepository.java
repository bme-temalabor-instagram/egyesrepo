package hu.bme.instagram.dal;

import hu.bme.instagram.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, String> {
}
