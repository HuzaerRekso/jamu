package com.swn.jamu.dto;

public class BranchStockDTO {

    private Long id;
    private Long branchId;
    private Long baseJamuId;
    private Long qty;

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

    public Long getBaseJamuId() {
        return baseJamuId;
    }

    public void setBaseJamuId(Long baseJamuId) {
        this.baseJamuId = baseJamuId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
}
