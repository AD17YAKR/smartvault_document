package com.smartvault.document.document.controller;

import com.smartvault.document.document.dto.ProductDTO;
import com.smartvault.document.document.model.Product;
import com.smartvault.document.document.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/smartvault/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping({ "/create", "/create/" })
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product savedProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PatchMapping({ "/update/{id}", "/update/{id}/" })
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping({ "/delete/{id}", "/delete/{id}/" })
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
