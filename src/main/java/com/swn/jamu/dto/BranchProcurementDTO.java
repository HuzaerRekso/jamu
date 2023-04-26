package com.swn.jamu.dto;

import java.util.List;

public class BranchProcurementDTO {

    private Long id;
    private Long branchId;
    private String branchName;
    private String status;
    private String requestDate;
    private String approvedDate;
    private String rejectDate;
    private String acceptDate;
    private String cancelDate;
    private String branchNotes;
    private String distributorNotes;
    private List<BranchProcurementDetailDTO> details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public String getBranchNotes() {
        return branchNotes;
    }

    public void setBranchNotes(String branchNotes) {
        this.branchNotes = branchNotes;
    }

    public String getDistributorNotes() {
        return distributorNotes;
    }

    public void setDistributorNotes(String distributorNotes) {
        this.distributorNotes = distributorNotes;
    }

    public List<BranchProcurementDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<BranchProcurementDetailDTO> details) {
        this.details = details;
    }
}
