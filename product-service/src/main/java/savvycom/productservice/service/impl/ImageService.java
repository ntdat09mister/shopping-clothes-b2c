package savvycom.productservice.service.impl;
//@Service hold the business handling code in it

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import savvycom.productservice.domain.entity.Image;
import savvycom.productservice.repository.ImageRepository;
import savvycom.productservice.service.IImageService;

import java.util.List;

@Service
public class ImageService implements IImageService {
    private ImageRepository imageRepository;
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Page<Image> findAll(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Image> findByProductId(Long productId) {
        return imageRepository.findByProductId(productId);
    }
}
