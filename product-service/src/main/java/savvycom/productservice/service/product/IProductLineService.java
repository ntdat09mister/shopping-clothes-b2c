package savvycom.productservice.service.product;

import org.hibernate.sql.Update;
import savvycom.productservice.domain.dto.CreateProductDTO;
import savvycom.productservice.domain.dto.ProductLineDTO;
import savvycom.productservice.domain.dto.UpdateProductDTO;
import savvycom.productservice.domain.entity.Discount;
import savvycom.productservice.domain.entity.product.ProductLine;

import java.util.List;

public interface IProductLineService {
    ProductLine save(ProductLine productLine);

    List<ProductLine> findAll();

   ProductLine findById(Long id);

   ProductLineDTO findProductLineDTOById(Long id);

    List<ProductLine> findByCategoryId(Long categoryId);

    List<ProductLine> findByNameLike(String name);

    void createProductDTO(CreateProductDTO createProductDTO);

    void updateProductDTO(UpdateProductDTO updateProductDTO);
}
