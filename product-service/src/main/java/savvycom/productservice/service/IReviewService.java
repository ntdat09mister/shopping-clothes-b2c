package savvycom.productservice.service;

import savvycom.productservice.domain.entity.Review;

import java.util.List;

public interface IReviewService {
    Review save(Review review);

    List<Review> findAll();

    List<Review> findReviewByProductId(Long productId);

    Review findById(Long id);
}
