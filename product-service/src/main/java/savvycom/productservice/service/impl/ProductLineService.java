package savvycom.productservice.service.impl;
//@Service hold the business handling code in it

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import savvycom.productservice.domain.dto.CreateProductDTO;
import savvycom.productservice.domain.dto.ProductLineDTO;
import savvycom.productservice.domain.dto.UpdateProductDTO;
import savvycom.productservice.domain.entity.Branch;
import savvycom.productservice.domain.entity.Image;
import savvycom.productservice.domain.entity.product.Inventory;
import savvycom.productservice.domain.entity.product.Product;
import savvycom.productservice.domain.entity.product.ProductLine;
import savvycom.productservice.exception.ProductNotFoundException;
import savvycom.productservice.repository.BranchRepository;
import savvycom.productservice.repository.ImageRepository;
import savvycom.productservice.repository.product.InventoryRepository;
import savvycom.productservice.repository.product.ProductLineRepository;
import savvycom.productservice.repository.product.ProductRepository;
import savvycom.productservice.service.IBranchService;
import savvycom.productservice.service.product.IProductLineService;
import savvycom.productservice.service.product.IProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductLineService implements IProductLineService {
    private final ProductLineRepository productLineRepository;

    private final IProductService productService;

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    private final ImageRepository imageRepository;

    private final BranchRepository branchRepository;

    @Override
    public ProductLine save(ProductLine productLine) {
        return productLineRepository.save(productLine);
    }

    @Override
    public List<ProductLine> findAll() {
        return productLineRepository.findAll();
    }
    @Override
    public ProductLine findById(Long id) {
        return productLineRepository.findById(id).orElse(null);
    }

    @Override
    public ProductLineDTO findProductLineDTOById(Long id) {
       ProductLine productLine = productLineRepository.findById(id).orElse(null);
       if(productLine != null){
           return ProductLineDTO.builder()
                   .id(productLine.getId())
                   .name(productLine.getName())
                   .desc(productLine.getDesc())
                   .categoryId(productLine.getCategoryId())
                   .productDTOs(productService.findListProductDTOByProductLineId(productLine.getId()))
                   .build();
       }
       return null;
    }

    @Override
    public List<ProductLine> findByCategoryId(Long categoryId) {
        return productLineRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<ProductLine> findByNameLike(String name) {

        return productLineRepository.findByNameLike(name);
    }

    @Override
    public void createProductDTO(CreateProductDTO createProductDTO) {
        ProductLine productLine = ProductLine.builder()
                .name(createProductDTO.getName())
                .desc(createProductDTO.getDesc())
                .categoryId(createProductDTO.getCategoryId())
                .active(1L)
                .createdAt(LocalDateTime.now())
                .build();
        productLine = productLineRepository.save(productLine);

        final Long productLineId = productLine.getId();

        List<Product> products = createProductDTO.getOptions()
                .stream()
                .map(option -> Product.builder()
                .color(option.getColor())
                .size(option.getSize())
                .price(option.getPrice())
                .active(1L)
                        .createdAt(LocalDateTime.now())
                .build())
                .peek(product -> product.setProductLineId(productLineId)).collect(Collectors.toList());
        products = productRepository.saveAll(products);

        List<Image> imageList = new ArrayList<>();
        for (int i = 0; i < createProductDTO.getOptions().size(); i++) {
            List<Image> images = new ArrayList<>();
            List<String> imageUrls = createProductDTO.getOptions().get(i).getImages();
            for (String imageUrl : imageUrls) {
                Image image = Image.builder()
                        .productId(products.get(i).getId())
                        .url(imageUrl)
                        .desc(productLine.getDesc())
                        .build();
                images.add(image);
            }
            imageList.addAll(images);
        }

        imageRepository.saveAll(imageList);

        List<Inventory> inventories = new ArrayList<>();
        for (int i = 0; i < createProductDTO.getOptions().size(); i++) {
            Long quantity = createProductDTO.getOptions().get(i).getQuantity();
            List<Branch> branches = branchRepository.findAll();

            for(Branch branch : branches) {
                Inventory inventory = Inventory.builder()
                        .productId(products.get(i).getId())
                        .branchId(branch.getId())
                        .quantity(quantity)
                        .createdAt(LocalDateTime.now())
                        .build();
                inventories.add(inventory);
            };
        }
        inventoryRepository.saveAll(inventories);
    }

    @Override
    public void updateProductDTO(UpdateProductDTO updateProductDTO) {
        ProductLine productLine = productLineRepository.findById(updateProductDTO.getProductLineId()).orElseThrow(() ->
                new ProductNotFoundException("Not found any productLine with id " + updateProductDTO.getProductLineId()));
        if (Objects.nonNull(updateProductDTO.getProductLineId()))
            productLine.setId(updateProductDTO.getProductLineId());
        if (Objects.nonNull(updateProductDTO.getName())) productLine.setName(updateProductDTO.getName());
        if (Objects.nonNull(updateProductDTO.getCategoryId()))
            productLine.setCategoryId(updateProductDTO.getCategoryId());

        productLine = productLineRepository.save(productLine);

        Product products = productRepository.findById(updateProductDTO.getProductId()).orElseThrow(() ->
                new ProductNotFoundException("Not found any product with id " + updateProductDTO.getProductId()));
        if (Objects.nonNull(updateProductDTO.getProductId())) products.setId(updateProductDTO.getProductId());
        if (Objects.nonNull(updateProductDTO.getColor())) products.setColor(updateProductDTO.getColor());
        if (Objects.nonNull(updateProductDTO.getSize())) products.setSize(updateProductDTO.getSize());
        if (Objects.nonNull(updateProductDTO.getPrice())) products.setPrice(updateProductDTO.getPrice());
        products = productRepository.save(products);

        List<Image> imageList = new ArrayList<>();
        for (int i = 0; i < updateProductDTO.getImages().size(); i++) {
            List<Image> images = new ArrayList<>();
            List<String> imageUrls = updateProductDTO.getImages();
            for (String imageUrl : imageUrls) {
                Image image = Image.builder()
                        .productId(products.getId())
                        .url(imageUrl)
                        .desc(productLine.getDesc())
                        .build();
                images.add(image);
            }
            imageList.addAll(images);
        }
        imageRepository.saveAll(imageList);

        Inventory inventory = inventoryRepository.findById(updateProductDTO.getProductId()).orElseThrow(() ->
                new ProductNotFoundException("Not found any inventory with id " + updateProductDTO.getProductId()));
        if (Objects.nonNull(updateProductDTO.getProductId())) inventory.setProductId(updateProductDTO.getProductId());

        inventory = inventoryRepository.save(inventory);
    }
}
