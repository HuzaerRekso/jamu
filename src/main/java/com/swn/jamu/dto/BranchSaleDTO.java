package com.swn.jamu.dto;

import java.util.List;

public class BranchSaleDTO {

    private Long id;
    private Long branchId;
    private String branchName;
    private String notes;
    private String saleDate;
    private Long totalSale;
    private List<BranchSaleDetailDTO> details;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public Long getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Long totalSale) {
        this.totalSale = totalSale;
    }

    public List<BranchSaleDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<BranchSaleDetailDTO> details) {
        this.details = details;
    }
}
