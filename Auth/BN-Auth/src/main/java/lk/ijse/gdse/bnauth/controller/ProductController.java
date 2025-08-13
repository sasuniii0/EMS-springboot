package lk.ijse.gdse.bnauth.controller;

import lk.ijse.gdse.bnauth.dto.APIResponse;
import lk.ijse.gdse.bnauth.dto.ProductDTO;
import lk.ijse.gdse.bnauth.entity.Product;
import lk.ijse.gdse.bnauth.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        APIResponse response = new APIResponse(200, "Products retrieved successfully", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(new APIResponse(200, "Product created successfully", savedProduct));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(new APIResponse(200, "Product retrieved successfully", product));
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateProduct(@RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(new APIResponse(200, "Product updated successfully", updatedProduct));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new APIResponse(200, "Product deleted successfully", null));
    }
}
