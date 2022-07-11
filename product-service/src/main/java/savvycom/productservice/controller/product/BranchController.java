package savvycom.productservice.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import savvycom.productservice.controller.BaseController;
import savvycom.productservice.repository.BranchRepository;
import savvycom.productservice.service.IBranchService;
import savvycom.productservice.utils.AppConstants;

import java.util.Base64;

@RequiredArgsConstructor
@RestController
@RequestMapping("branch")
public class BranchController extends BaseController {
    @Autowired
    private IBranchService branchService;

    @GetMapping("{id}")
    public ResponseEntity<?> findByBranch(@PathVariable Long id)
    {
        return successResponse(branchService.findById(id));
    }
}
