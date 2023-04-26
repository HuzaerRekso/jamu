package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class BranchProcurement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Branch branch;
    private String status;
    private LocalDate requestDate;
    private LocalDate approvedDate;
    private LocalDate rejectDate;
    private LocalDate acceptDate;
    private LocalDate cancelDate;
    private String branchNotes;
    private String distributorNotes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchProcurement")
    private List<BranchProcurementDetail> details;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    public LocalDate getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(LocalDate rejectDate) {
        this.rejectDate = rejectDate;
    }

    public LocalDate getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(LocalDate acceptDate) {
        this.acceptDate = acceptDate;
    }

    public LocalDate getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDate cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getBranchNotes() {
        return branchNotes;
    }

    public void setBranchNotes(String branchNotes) {
        this.branchNotes = branchNotes;
    }

    public String getDistributorNotes() {
        return distributorNotes;
    }

    public void setDistributorNotes(String distributorNotes) {
        this.distributorNotes = distributorNotes;
    }

    public List<BranchProcurementDetail> getDetails() {
        return details;
    }

    public void setDetails(List<BranchProcurementDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "BranchProcurement{" +
                "id=" + id +
                ", branch=" + branch +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                ", approvedDate=" + approvedDate +
                ", rejectDate=" + rejectDate +
                ", acceptDate=" + acceptDate +
                ", cancelDate=" + cancelDate +
                ", branchNotes='" + branchNotes + '\'' +
                ", distributorNotes='" + distributorNotes + '\'' +
                ", details=" + details +
                '}';
    }
}
