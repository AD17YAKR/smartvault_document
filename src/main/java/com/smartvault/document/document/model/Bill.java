package com.smartvault.document.document.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "billing_date", nullable = false)
    private LocalDateTime billingDate;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "warranty_tenure", nullable = false)
    private Integer warrantyTenure;

    @Column(name = "pdf_url", nullable = false, columnDefinition = "TEXT")
    private String pdfUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
