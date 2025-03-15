package com.smartvault.document.document.controller;

import com.smartvault.document.document.dto.BillDTO;
import com.smartvault.document.document.model.Bill;
import com.smartvault.document.document.service.BillService;

import jakarta.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/smartvault/bill")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping({ "/create", "/create/" })
    public ResponseEntity<Bill> createBill(@RequestBody BillDTO billDTO) {
        Bill savedBill = billService.createBill(billDTO);
        return ResponseEntity.ok(savedBill);
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<BillDTO> getBillById(@PathVariable Long id) {
        BillDTO billDTO = billService.getBillById(id);
        return ResponseEntity.ok(billDTO);
    }

    @Transactional
    @PatchMapping({ "/update/{id}", "/update/{id}/" })
    public ResponseEntity<Bill> updateBill(@PathVariable Long id, @RequestBody BillDTO billDTO) {
        Bill updatedBill = billService.updateBill(id, billDTO);
        return ResponseEntity.ok(updatedBill);
    }

    @DeleteMapping({ "/delete/{id}", "/delete/{id}/" })
    public ResponseEntity<String> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.ok("Bill deleted successfully");
    }
}
