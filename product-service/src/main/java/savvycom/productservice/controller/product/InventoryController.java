package savvycom.productservice.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import savvycom.productservice.common.Const;
import savvycom.productservice.controller.BaseController;
import savvycom.productservice.domain.dto.InventoryDTO;
import savvycom.productservice.domain.entity.product.Inventory;
import savvycom.productservice.domain.message.ResponseMessage;
import savvycom.productservice.service.product.IInventoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController extends BaseController {
    @Autowired
    private IInventoryService inventoryService;
    @Autowired
    public InventoryController(IInventoryService InventoryService) {
        this.inventoryService = InventoryService;
    }
    /**
     * Create new product inventory
     * @return successResponse
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Create new inventory")
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_OK_STR, description = "Create product inventory completed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_BAD_REQUEST_STR, description = "Input invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_INTERNAL_SERVER_ERROR_STR, description = "Internal Server Error",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    public ResponseEntity<?> newInventory(@RequestBody Inventory inventory){
        return successResponse(inventoryService.save(inventory));
    }
    /**
     * Update quantity while user order
     * @return successResponse
     */
    @PostMapping("/quantity")
    @Operation(summary = "Update quantity, user order")
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_OK_STR, description = "Update quantity while order completed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_BAD_REQUEST_STR, description = "Input invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_INTERNAL_SERVER_ERROR_STR, description = "Internal Server Error",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    public ResponseEntity<?> updateInventoryQuantity(@RequestBody List<InventoryDTO> inventoryDTOs) {
        inventoryService.updateQuantities(inventoryDTOs);
        return successResponse();
    }
    /**
     * Update product inventory
     * @return successResponse
     */
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Update inventory by id")
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_OK_STR, description = "Update product inventory completed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_BAD_REQUEST_STR, description = "Input invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    @ApiResponse(responseCode = Const.API_RESPONSE.API_STATUS_INTERNAL_SERVER_ERROR_STR, description = "Internal Server Error",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class))})
    public ResponseEntity<?> updateInventory(@PathVariable("id") Long id, @RequestBody Inventory inventory)
    {
        inventory.setId(id);
        return successResponse(inventoryService.save(inventory));
    }
}