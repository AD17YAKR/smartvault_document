package com.smartvault.document.document.service;

import com.smartvault.document.document.dto.ProductDTO;
import com.smartvault.document.document.exception.ProductException;
import com.smartvault.document.document.mapper.ProductMapper;
import com.smartvault.document.document.model.Bill;
import com.smartvault.document.document.model.Product;
import com.smartvault.document.document.repository.BillRepository;
import com.smartvault.document.document.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BillRepository billRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
            BillRepository billRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.billRepository = billRepository;
    }

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = productMapper.convertToEntity(productDTO);
        Bill bill = billRepository.findById(productDTO.getBillId())
                .orElseThrow(() -> new ProductException("Bill not found with id: " + productDTO.getBillId()));
        product.setBill(bill);
        return productRepository.save(product);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id));
        return productMapper.convertToDTO(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id));

        if (productDTO.getBrand() != null) {
            existingProduct.setBrand(productDTO.getBrand());
        }
        if (productDTO.getType() != null) {
            existingProduct.setType(productDTO.getType());
        }
        if (productDTO.getName() != null) {
            existingProduct.setName(productDTO.getName());
        }
        if (productDTO.getModelNumber() != null) {
            existingProduct.setModelNumber(productDTO.getModelNumber());
        }
        if (productDTO.getBillId() != null) {
            Bill bill = billRepository.findById(productDTO.getBillId())
                    .orElseThrow(() -> new ProductException("Bill not found with id: " + productDTO.getBillId()));
            existingProduct.setBill(bill);
        }

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
