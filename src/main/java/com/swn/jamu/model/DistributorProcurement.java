package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class DistributorProcurement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private LocalDate requestDate;
    private LocalDate approvedDate;
    private LocalDate rejectDate;
    private LocalDate acceptDate;
    private LocalDate cancelDate;
    private String distributorNotes;
    private String supplierNotes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "distributorProcurement")
    private List<DistributorProcurementDetail> details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    public LocalDate getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(LocalDate rejectDate) {
        this.rejectDate = rejectDate;
    }

    public LocalDate getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(LocalDate acceptDate) {
        this.acceptDate = acceptDate;
    }

    public LocalDate getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDate cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getSupplierNotes() {
        return supplierNotes;
    }

    public void setSupplierNotes(String supplierNotes) {
        this.supplierNotes = supplierNotes;
    }

    public String getDistributorNotes() {
        return distributorNotes;
    }

    public void setDistributorNotes(String distributorNotes) {
        this.distributorNotes = distributorNotes;
    }

    public List<DistributorProcurementDetail> getDetails() {
        return details;
    }

    public void setDetails(List<DistributorProcurementDetail> details) {
        this.details = details;
    }
}
