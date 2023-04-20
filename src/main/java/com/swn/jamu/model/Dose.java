package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Dose extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BaseJamu baseJamu;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Jamu jamu;
    private Long dose;

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

    public Jamu getJamu() {
        return jamu;
    }

    public void setJamu(Jamu jamu) {
        this.jamu = jamu;
    }

    public Long getDose() {
        return dose;
    }

    public void setDose(Long dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "Dose{" +
                "id=" + id +
                ", baseJamu=" + baseJamu +
                ", jamu=" + jamu +
                ", dose=" + dose +
                '}';
    }
}
