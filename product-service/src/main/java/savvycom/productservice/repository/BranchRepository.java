package savvycom.productservice.repository;
// @Repo access database, class click database.

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savvycom.productservice.domain.entity.Branch;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findById(Long id);

}
