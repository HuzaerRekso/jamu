package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class DistributorStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BaseJamu baseJamu;
    private Long qty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                ", baseJamu=" + baseJamu +
                ", qty=" + qty +
                '}';
    }
}
