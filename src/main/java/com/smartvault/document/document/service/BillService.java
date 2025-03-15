package com.smartvault.document.document.service;

import com.smartvault.document.document.dto.BillDTO;
import com.smartvault.document.document.exception.BillException;
import com.smartvault.document.document.mapper.BillMapper;
import com.smartvault.document.document.model.Bill;
import com.smartvault.document.document.repository.BillRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final BillMapper billMapper;

    public BillService(BillRepository billRepository, BillMapper billMapper) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
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
}
