package com.swn.jamu.dto;

import java.util.List;

public class DistributorProcurementDTO {

    private Long id;
    private String status;
    private String requestDate;
    private String approvedDate;
    private String rejectDate;
    private String acceptDate;
    private String cancelDate;
    private String distributorNotes;
    private String supplierNotes;
    private List<DistributorProcurementDetailDTO> details;

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

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(String rejectDate) {
        this.rejectDate = rejectDate;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getDistributorNotes() {
        return distributorNotes;
    }

    public void setDistributorNotes(String distributorNotes) {
        this.distributorNotes = distributorNotes;
    }

    public String getSupplierNotes() {
        return supplierNotes;
    }

    public void setSupplierNotes(String supplierNotes) {
        this.supplierNotes = supplierNotes;
    }

    public List<DistributorProcurementDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<DistributorProcurementDetailDTO> details) {
        this.details = details;
    }
}
