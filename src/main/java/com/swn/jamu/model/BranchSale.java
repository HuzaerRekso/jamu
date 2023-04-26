package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class BranchSale extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Branch branch;
    private String notes;
    private LocalDate saleDate;
    private Long totalSale;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchSale")
    private List<BranchSaleDetail> details;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Long getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Long totalSale) {
        this.totalSale = totalSale;
    }

    public List<BranchSaleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<BranchSaleDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "BranchSale{" +
                "id=" + id +
                ", branch=" + branch +
                ", notes='" + notes + '\'' +
                ", saleDate=" + saleDate +
                ", totalSale=" + totalSale +
                ", details=" + details +
                '}';
    }
}
