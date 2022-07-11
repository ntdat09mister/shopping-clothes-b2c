package savvycom.productservice.service;

import savvycom.productservice.domain.entity.Discount;
import savvycom.productservice.domain.entity.product.ProductLine;

import java.util.List;

public interface IDiscountService {
    Discount save(Discount discount);

    List<Discount> findAll();

    Discount findById(Long id);

}
