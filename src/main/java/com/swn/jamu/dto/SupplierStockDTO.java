package com.swn.jamu.dto;

public class SupplierStockDTO {

    private Long id;
    private Long baseJamuId;
    private String baseJamuName;
    private String baseJamuCode;
    private Long qty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBaseJamuId() {
        return baseJamuId;
    }

    public void setBaseJamuId(Long baseJamuId) {
        this.baseJamuId = baseJamuId;
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

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
}
