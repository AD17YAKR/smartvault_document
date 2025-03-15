package com.smartvault.document.document.mapper;


import com.smartvault.document.document.dto.BillDTO;
import com.smartvault.document.document.model.Bill;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BillMapper {

    private final ModelMapper modelMapper;

    public BillMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BillDTO convertToDTO(Bill bill) {
        return modelMapper.map(bill, BillDTO.class);
    }

    public Bill convertToEntity(BillDTO billDTO) {
        return modelMapper.map(billDTO, Bill.class);
    }
}
