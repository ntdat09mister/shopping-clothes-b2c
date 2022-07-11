package savvycom.productservice.repository.product;
// @Repo access database, class click database.

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savvycom.productservice.domain.entity.product.Category;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findById(Long id);

}
