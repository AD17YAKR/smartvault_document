package com.smartvault.document.document.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BillDTO {
    private Long id;
    private LocalDateTime billingDate;
    private String storeName;
    private Integer warrantyTenure;
    private String pdfUrl;

}
