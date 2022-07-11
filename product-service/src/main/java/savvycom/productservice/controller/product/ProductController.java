package savvycom.productservice.controller.product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import savvycom.productservice.common.Const;
import savvycom.productservice.controller.BaseController;
import savvycom.productservice.domain.entity.product.Product;
import savvycom.productservice.domain.entity.product.ProductLine;
import savvycom.productservice.domain.message.ResponseMessage;
import savvycom.productservice.service.product.IProductLineService;
import savvycom.productservice.service.product.IProductService;
import savvycom.productservice.utils.AppConstants;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController extends BaseController {
    private final IProductService productService;
    private final IProductLineService productLineService;

    /**
     * Create new Product
     * @return successResponse
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Create new product")
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_OK_STR, description = "create completed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_BAD_REQUEST_STR, description = "Input invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_INTERNAL_SERVER_ERROR_STR, description = "Internal Server Error",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        productService.save(product);
        return successResponse("create completed");
    }

    /**
     * Delete Product by admin
     * @Param Long id
     * @return successResponse
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Delete product set active")
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_OK_STR, description = "delete completed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_BAD_REQUEST_STR, description = "Input invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_INTERNAL_SERVER_ERROR_STR, description = "Internal Server Error",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return successResponse("Deleted successfully!");
    }
    /**
     * Insert Product by admin
     * @Param Long id
     * @return successResponse
     */
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Update product")
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_OK_STR, description = "Update completed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_BAD_REQUEST_STR, description = "Input invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_INTERNAL_SERVER_ERROR_STR, description = "Internal Server Error",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    public ResponseEntity<?> insertProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        product.setId(id);
        productService.save(product);
        return successResponse("Update completed");
    }

    /**
     * Find all productOutput by id
     * @Param Long id
     * @return successResponse with List<ProductOutput>
     */
    @GetMapping("/{id}")
    @Operation(summary = "Find ProductResponse By id")
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_OK_STR, description = "Find ProductOutput by id completed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_BAD_REQUEST_STR, description = "Input invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_INTERNAL_SERVER_ERROR_STR, description = "Internal Server Error",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    public ResponseEntity<?> findProductResponseById(@PathVariable("id") Long id) {
        return successResponse(productService.findProductOutputById(id));
    }
    /**
     * Filter,search product, search categoryName by categoryId, find product detail
     * @Param productDetailId, categoryId, name, color, size, priceFrom, priceTo
     * @return successResponse with ProductResponse
     */
    @GetMapping("")
    @Operation(summary = "Filter,search product name, search categoryName, find productDetail")
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_OK_STR, description = "Find all Product completed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_BAD_REQUEST_STR, description = "Input invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_INTERNAL_SERVER_ERROR_STR, description = "Internal Server Error",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    public ResponseEntity<?> findAllProductAndProductDetailByIdAndFilterAndSearchProduct(
    @RequestParam(value = "productDetailId", required = false) Long productDetailId,
    @RequestParam(value = "categoryId", required = false) Long categoryId,
    @RequestParam(value = "name", required = false) String name,
    @RequestParam(value = "size", required = false) String size,
    @RequestParam(value = "color", required = false) String color,
    @RequestParam(value = "priceFrom", defaultValue = AppConstants.DEFAULT_PRICE_FROM, required = false) Long priceFrom,
    @RequestParam(value = "priceTo", defaultValue = AppConstants.DEFAULT_PRICE_TO, required = false) Long priceTo,
    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {
        // find all productOutput
        if (productDetailId == null && categoryId == null && name == null && size == null && color == null)
        {
            PageImpl<?> productOutput = productService.findAllProductOutput(pageNo, pageSize, sortBy, sortDir);
            return successResponse(productOutput);
        }
        // find product detail
         else if(productDetailId != null && categoryId == null && name == null && size == null && color == null){
            return successResponse(productLineService.findProductLineDTOById(productDetailId));
         }
         // search categoryName by categoryId in productLine
         else if(productDetailId == null && categoryId != null && name == null && size == null && color == null)
         {
            List<ProductLine> productLines1 = productLineService.findByCategoryId(categoryId);
            List<Long> productLineIds = productLines1.stream().map(productLine -> productLine.getId()).collect(Collectors.toList());
            PageImpl<?> productResponse1 = productService.findByProductLineId(productLineIds, pageNo, pageSize, sortBy, sortDir);
            return successResponse(productResponse1);
         }
         // search name product
         else if(productDetailId == null && categoryId == null && name != null && size == null && color == null)
         {
            name = "%" + name + "%";
            List<ProductLine> productLines = productLineService.findByNameLike(name);
            List<Long> productLineIds = productLines.stream().map(productLine -> productLine.getId()).collect(Collectors.toList());
            PageImpl<?> productResponse2 = productService.findByProductLineId(productLineIds, pageNo, pageSize, sortBy, sortDir);
            return successResponse(productResponse2);
         }
         //filter color, size, price
         else if(productDetailId == null && categoryId == null && name == null && size != null && color != null)
         {
            PageImpl<?> productResponse3 = productService.findByColorAndSizeAndPriceBetween(color, size,
                        priceFrom, priceTo, pageNo, pageSize, sortBy, sortDir);
            return successResponse(productResponse3);
         }
        //filter size, price
        else if(productDetailId == null && categoryId == null && name == null && size != null && color == null)
        {
            PageImpl<?> productResponse4 = productService.findBySizeAndPriceBetween(size,
                    priceFrom, priceTo, pageNo, pageSize, sortBy, sortDir);
            return successResponse(productResponse4);
        }
        //filter color, price
        else if(productDetailId == null && categoryId == null && name == null && size == null && color != null)
        {
            PageImpl<?> productResponse5 = productService.findByColorAndPriceBetween
                    (color, priceFrom, priceTo, pageNo, pageSize, sortBy, sortDir);
            return successResponse(productResponse5);
        }
        else
        {
            return successResponse("Bad Request");
        }
    }

}
