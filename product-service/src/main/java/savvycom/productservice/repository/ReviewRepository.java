package savvycom.productservice.repository;
// @Repo access database, class click database.

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savvycom.productservice.domain.entity.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    Optional<Review> findById(Long id);

    List<Review> findReviewByProductId(Long productId);
}
