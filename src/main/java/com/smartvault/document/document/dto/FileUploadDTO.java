package com.smartvault.document.document.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadDTO {
    private MultipartFile file;
    private String storeName;
    private Integer warrantyTenure;
}
