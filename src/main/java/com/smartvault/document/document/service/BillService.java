package com.smartvault.document.document.service;

import com.smartvault.document.document.dto.BillDTO;
import com.smartvault.document.document.dto.FileUploadDTO;
import com.smartvault.document.document.exception.BillException;
import com.smartvault.document.document.mapper.BillMapper;
import com.smartvault.document.document.model.Bill;
import com.smartvault.document.document.repository.BillRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final BillMapper billMapper;
    private final BackblazeService backblazeService;

    public BillService(BillRepository billRepository, BillMapper billMapper,
            BackblazeService backblazeService) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
        this.backblazeService = backblazeService;
    }

    @Transactional
    public Bill createBill(BillDTO billDTO) {
        Bill bill = billMapper.convertToEntity(billDTO);
        return billRepository.save(bill);
    }

    public BillDTO getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillException("Bill not found with id: " + id));
        return billMapper.convertToDTO(bill);
    }

    @Transactional
    public Bill updateBill(Long id, BillDTO billDTO) {
        Bill existingBill = billRepository.findById(id)
                .orElseThrow(() -> new BillException("Bill not found with id: " + id));

        if (billDTO.getBillingDate() != null) {
            existingBill.setBillingDate(billDTO.getBillingDate());
        }
        if (billDTO.getStoreName() != null) {
            existingBill.setStoreName(billDTO.getStoreName());
        }
        if (billDTO.getWarrantyTenure() != null) {
            existingBill.setWarrantyTenure(billDTO.getWarrantyTenure());
        }
        if (billDTO.getPdfUrl() != null) {
            existingBill.setPdfUrl(billDTO.getPdfUrl());
        }

        return billRepository.save(existingBill);
    }

    @Transactional
    public void deleteBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillException("Bill not found with id: " + id));
        billRepository.delete(bill);
    }

    @Transactional
    public Bill uploadBill(FileUploadDTO fileUploadDTO) {
        MultipartFile file = fileUploadDTO.getFile();

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File must not be empty");
        }

        try {
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            Path filePath = tempFile.toPath();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = backblazeService.uploadFile(file.getOriginalFilename(), filePath);

            tempFile.deleteOnExit();

            Bill bill = new Bill();
            bill.setBillingDate(LocalDateTime.now());
            bill.setStoreName(fileUploadDTO.getStoreName());
            bill.setWarrantyTenure(fileUploadDTO.getWarrantyTenure());
            bill.setPdfUrl(fileUrl);

            return billRepository.save(bill);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }
}
