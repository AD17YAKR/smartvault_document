package com.smartvault.document.document.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String brand;
    private String type;
    private String name;
    private String modelNumber;
    private Long billId;

}
