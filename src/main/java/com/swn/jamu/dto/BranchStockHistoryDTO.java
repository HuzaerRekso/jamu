package com.swn.jamu.dto;

public class BranchStockHistoryDTO {

    private Long id;
    private Long branchStockId;
    private String baseJamuName;
    private String baseJamuCode;
    private String stockDate;
    private Long qty;
    private Long previousQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchStockId() {
        return branchStockId;
    }

    public void setBranchStockId(Long branchStockId) {
        this.branchStockId = branchStockId;
    }

    public String getBaseJamuName() {
        return baseJamuName;
    }

    public void setBaseJamuName(String baseJamuName) {
        this.baseJamuName = baseJamuName;
    }

    public String getBaseJamuCode() {
        return baseJamuCode;
    }

    public void setBaseJamuCode(String baseJamuCode) {
        this.baseJamuCode = baseJamuCode;
    }

    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getPreviousQty() {
        return previousQty;
    }

    public void setPreviousQty(Long previousQty) {
        this.previousQty = previousQty;
    }
}
