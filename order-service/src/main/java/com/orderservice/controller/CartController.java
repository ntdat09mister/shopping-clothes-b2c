package com.orderservice.controller;

import com.orderservice.common.DefaultPagination;
import com.orderservice.common.HttpStatusCode;
import com.orderservice.domain.dto.input.CartUpdateDTO;
import com.orderservice.domain.dto.input.CartInsertDTO;
import com.orderservice.domain.message.BaseMessage;
import com.orderservice.domain.model.output.User;
import com.orderservice.resolver.UserInfo;
import com.orderservice.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController extends BaseController {
    private final CartService cartService;

    /**
     * Find all cart
     *
     * @return successResponse + List<CartDTO>
     */
    @GetMapping
    @Operation(summary = "Get all carts of the logged in user")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Find successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> findAll(@UserInfo User user,
                                     @RequestParam(required = false, defaultValue = DefaultPagination.PAGE_NUMBER) Integer pageNo,
                                     @RequestParam(required = false, defaultValue = DefaultPagination.PAGE_SIZE) Integer pageSize,
                                     @RequestParam(required = false, defaultValue = DefaultPagination.SORT_BY) String sortBy,
                                     @RequestParam(required = false, defaultValue = DefaultPagination.SORT_DIRECTION) String sortDir
    ) {
        return successResponse(cartService.findAll(user.getId(), pageNo, pageSize, sortBy, sortDir));
    }

    /**
     * Insert new cart
     * Input cartDTOInsert
     *
     * @return successResponse
     */
    @PostMapping
    @PreAuthorize("@userSecurity.hasUserId(authentication,#cart.userId)")
    @Operation(summary = "Add new cart")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Add successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> add(@RequestBody @Valid CartInsertDTO cart) {
        cartService.insert(cart);
        return successResponse("Insert cart successfully!");
    }

    /**
     * Update cart
     *
     * @param cart
     * @return responseCode
     */
    @PutMapping
    @PreAuthorize("@userSecurity.hasUserId(authentication,#cart.userId)")
    @Operation(summary = "Update Cart")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Update successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> update(@RequestBody CartUpdateDTO cart) {
        cartService.update(cart);
        return successResponse("Update cart successfully!");
    }

    /**
     * Delete cart by cart id
     *
     * @param id
     * @return successResponse
     */
    @DeleteMapping("{id}")
    @Operation(summary = "Delete cart by cart id")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Delete successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        cartService.deleteById(id);
        return successResponse("Deleted successfully!");
    }

    /**
     * Delete cart by user id
     *
     * @param user
     * @return successResponse
     */
    @DeleteMapping("deleteByUserId")
    @Operation(summary = "Delete cart by user id")
    @ApiResponse(responseCode = HttpStatusCode.OK, description = "Delete successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = "Invalid input", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    @ApiResponse(responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY, description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BaseMessage.class))})
    public ResponseEntity<?> deleteByUserId(@UserInfo User user) {
        cartService.deleteByUserId(user.getId());
        return successResponse("Deleted successfully!");
    }
}
