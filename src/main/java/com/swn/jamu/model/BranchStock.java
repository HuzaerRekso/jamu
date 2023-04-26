package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class BranchStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Branch branch;
    @ManyToOne
    private BaseJamu baseJamu;
    private Long qty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public BaseJamu getBaseJamu() {
        return baseJamu;
    }

    public void setBaseJamu(BaseJamu baseJamu) {
        this.baseJamu = baseJamu;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "BranchStock{" +
                "id=" + id +
                ", branch=" + branch +
                ", baseJamu=" + baseJamu +
                ", qty=" + qty +
                '}';
    }
}
