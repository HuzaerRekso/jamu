package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class BranchSaleDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BranchSale branchSale;
    @ManyToOne
    private Jamu jamu;
    private Long qty;
    private Long price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BranchSale getBranchSale() {
        return branchSale;
    }

    public void setBranchSale(BranchSale branchSale) {
        this.branchSale = branchSale;
    }

    public Jamu getJamu() {
        return jamu;
    }

    public void setJamu(Jamu jamu) {
        this.jamu = jamu;
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

    @Override
    public String toString() {
        return "BranchSaleDetail{" +
                "id=" + id +
                ", branchSale=" + branchSale +
                ", jamu=" + jamu +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
