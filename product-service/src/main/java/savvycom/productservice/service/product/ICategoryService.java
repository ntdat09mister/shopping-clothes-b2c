package savvycom.productservice.service.product;
//handle bussiness
import org.springframework.data.domain.PageImpl;
import savvycom.productservice.domain.dto.CategoryDTO;
import savvycom.productservice.domain.dto.CategoryOutput;
import savvycom.productservice.domain.entity.product.Category;

import java.util.List;

public interface ICategoryService {
    Category save(Category category);

    List<Category> findAll();

    Category findById(Long id);

    List<CategoryOutput> findByAll();

}
