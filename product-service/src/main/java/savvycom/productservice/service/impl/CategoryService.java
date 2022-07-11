package savvycom.productservice.service.impl;
//@Service hold the business handling code in it

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import savvycom.productservice.domain.dto.CategoryOutput;
import savvycom.productservice.domain.entity.product.Category;
import savvycom.productservice.repository.product.CategoryRepository;
import savvycom.productservice.service.product.ICategoryService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        List<Category> category = categoryRepository.findAll();
        return category;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<CategoryOutput> findByAll() {
        List<Category> categories = findAll();
        return categories.stream().map(category -> CategoryOutput.builder()
                .id(category.getId())
                .name(category.getName())
                .desc(category.getDesc())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build()).collect(Collectors.toList());
    }

}
