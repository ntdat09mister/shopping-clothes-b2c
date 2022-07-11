package savvycom.productservice.repository.product;
// @Repo access database, class click database.

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import savvycom.productservice.domain.entity.product.Inventory;

import java.util.List;
import java.util.Optional;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findById(Long id);
    Inventory findByBranchIdAndProductId(Long branchId, Long productId);

    List<Inventory> findByProductId(Long productId);

    List<Inventory> findByBranchId(Long branchId);

}
