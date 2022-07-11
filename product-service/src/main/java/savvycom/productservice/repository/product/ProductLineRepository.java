package savvycom.productservice.repository.product;
// @Repo access database, class click database.
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savvycom.productservice.domain.entity.product.Product;
import savvycom.productservice.domain.entity.product.ProductLine;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
    List<ProductLine> findByNameLike(String name);
    Optional<ProductLine> findById(Long id);

    List<ProductLine> findByCategoryId(Long categoryId);

}
