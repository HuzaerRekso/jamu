package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class BranchProcurementDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BranchProcurement branchProcurement;
    @ManyToOne
    private BaseJamu baseJamu;
    private Long quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BranchProcurement getBranchProcurement() {
        return branchProcurement;
    }

    public void setBranchProcurement(BranchProcurement branchProcurement) {
        this.branchProcurement = branchProcurement;
    }

    public BaseJamu getBaseJamu() {
        return baseJamu;
    }

    public void setBaseJamu(BaseJamu baseJamu) {
        this.baseJamu = baseJamu;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


}
