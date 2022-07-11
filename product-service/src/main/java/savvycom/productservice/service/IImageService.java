package savvycom.productservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import savvycom.productservice.domain.entity.Image;

import java.util.List;

public interface IImageService {
    Image save(Image image);

    Page<Image> findAll(Pageable pageable);

    Image findById(Long id);

    List<Image> findByProductId(Long productId);

}
