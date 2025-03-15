package com.smartvault.document.document.mapper;

import com.smartvault.document.document.dto.ProductDTO;
import com.smartvault.document.document.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        if (product.getBill() != null) {
            productDTO.setBillId(product.getBill().getId());
        }
        return productDTO;
    }

    public Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
