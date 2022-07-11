package savvycom.productservice.repository;
// @Repo access database, class click database.

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savvycom.productservice.domain.entity.Image;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findById(Long id);
    List<Image> findByProductId(Long productId);
}
