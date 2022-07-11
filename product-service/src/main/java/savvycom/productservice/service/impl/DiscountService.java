package savvycom.productservice.service.impl;
//@Service hold the business handling code in it

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savvycom.productservice.domain.entity.Discount;
import savvycom.productservice.domain.entity.product.ProductLine;
import savvycom.productservice.repository.DiscountRepository;
import savvycom.productservice.repository.product.ProductLineRepository;
import savvycom.productservice.service.IDiscountService;

import java.util.List;

@Service
public class DiscountService implements IDiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Discount findById(Long id) {
        return discountRepository.findById(id).orElse(null);
    }


}
