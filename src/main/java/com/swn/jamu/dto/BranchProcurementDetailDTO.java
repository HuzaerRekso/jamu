package com.swn.jamu.dto;

public class BranchProcurementDetailDTO {

    private Long id;
    private Long branchProcurementId;
    private Long baseJamuId;
    private String baseJamuCode;
    private String baseJamuName;
    private Long quantity;
    private Boolean selected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchProcurementId() {
        return branchProcurementId;
    }

    public void setBranchProcurementId(Long branchProcurementId) {
        this.branchProcurementId = branchProcurementId;
    }

    public Long getBaseJamuId() {
        return baseJamuId;
    }

    public void setBaseJamuId(Long baseJamuId) {
        this.baseJamuId = baseJamuId;
    }

    public String getBaseJamuCode() {
        return baseJamuCode;
    }

    public void setBaseJamuCode(String baseJamuCode) {
        this.baseJamuCode = baseJamuCode;
    }

    public String getBaseJamuName() {
        return baseJamuName;
    }

    public void setBaseJamuName(String baseJamuName) {
        this.baseJamuName = baseJamuName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
