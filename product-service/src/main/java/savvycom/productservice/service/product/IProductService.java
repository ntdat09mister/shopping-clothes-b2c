package savvycom.productservice.service.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import savvycom.productservice.domain.dto.ProductDTO;
import savvycom.productservice.domain.dto.ProductOutput;
import savvycom.productservice.domain.entity.product.Product;

import java.util.List;

public interface IProductService {
    Product save(Product product);

    void deleteById(Long id);
    ProductOutput findProductOutputById(Long id);

    PageImpl<?> findByProductLineId(List<Long> productLineId, int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductDTO> findListProductDTOByProductLineId(Long productLineId);

    PageImpl<?> findByColorAndSizeAndPriceBetween(String color
            , String size, Long priceFrom, Long priceTo, int pageNo, int pageSize, String sortBy, String sortDir);
    PageImpl<?> findByColorAndPriceBetween(String color
            , Long priceFrom, Long priceTo, int pageNo, int pageSize, String sortBy, String sortDir);
    PageImpl<?> findBySizeAndPriceBetween(String size
            , Long priceFrom, Long priceTo, int pageNo, int pageSize, String sortBy, String sortDir);

    PageImpl<?> findAllProductOutput(int pageNo, int pageSize, String sortBy, String sortDir);

}


