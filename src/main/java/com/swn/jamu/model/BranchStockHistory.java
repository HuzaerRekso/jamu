package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BranchStockHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BranchStock branchStock;
    private LocalDate stockDate;
    private Long qty;
    private Long previousQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BranchStock getBranchStock() {
        return branchStock;
    }

    public void setBranchStock(BranchStock branchStock) {
        this.branchStock = branchStock;
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
        return "BranchStockHistory{" +
                "id=" + id +
                ", branchStock=" + branchStock +
                ", stockDate=" + stockDate +
                ", qty=" + qty +
                ", previousQty=" + previousQty +
                '}';
    }
}
