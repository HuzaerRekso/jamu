package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class DistributorStockHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private DistributorStock distributorStock;
    private LocalDate stockDate;
    private Long qty;
    private Long previousQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DistributorStock getDistributorStock() {
        return distributorStock;
    }

    public void setDistributorStock(DistributorStock distributorStock) {
        this.distributorStock = distributorStock;
    }

    public LocalDate getStockDate() {
        return stockDate;
    }

    public void setStockDate(LocalDate stockDate) {
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

    @Override
    public String toString() {
        return "DistributorStockHistory{" +
                "id=" + id +
                ", distributorStock=" + distributorStock +
                ", stockDate=" + stockDate +
                ", qty=" + qty +
                ", previousQty=" + previousQty +
                '}';
    }
}
