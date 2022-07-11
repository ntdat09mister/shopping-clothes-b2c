package savvycom.productservice.repository;
// @Repo access database, class click database.

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savvycom.productservice.domain.entity.Discount;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findById(Long id);

}
