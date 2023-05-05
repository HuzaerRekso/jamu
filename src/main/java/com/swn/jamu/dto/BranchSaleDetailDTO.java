package com.swn.jamu.dto;

public class BranchSaleDetailDTO {

    private Long id;
    private Long branchSaleId;
    private String saleDate;
    private Long jamuId;
    private String jamuCode;
    private String jamuName;
    private Long qty;
    private Long price;
    private Long pricePerQty;
    private Boolean selected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchSaleId() {
        return branchSaleId;
    }

    public void setBranchSaleId(Long branchSaleId) {
        this.branchSaleId = branchSaleId;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public Long getJamuId() {
        return jamuId;
    }

    public void setJamuId(Long jamuId) {
        this.jamuId = jamuId;
    }

    public String getJamuCode() {
        return jamuCode;
    }

    public void setJamuCode(String jamuCode) {
        this.jamuCode = jamuCode;
    }

    public String getJamuName() {
        return jamuName;
    }

    public void setJamuName(String jamuName) {
        this.jamuName = jamuName;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPricePerQty() {
        return pricePerQty;
    }

    public void setPricePerQty(Long pricePerQty) {
        this.pricePerQty = pricePerQty;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
