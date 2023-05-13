package com.swn.jamu.dto;

public class DoseDTO {

    private Long id;
    private Long baseJamuId;
    private String baseJamuCode;
    private String baseJamuName;
    private Long jamuId;
    private Long dose;
    private Boolean selected = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBaseJamuId() {
        return baseJamuId;
    }

    public void setBaseJamuId(Long baseJamuId) {
        this.baseJamuId = baseJamuId;
    }

    public String getBaseJamuCode() {
        return baseJamuCode;
    }

    public void setBaseJamuCode(String baseJamuCode) {
        this.baseJamuCode = baseJamuCode;
    }

    public String getBaseJamuName() {
        return baseJamuName;
    }

    public void setBaseJamuName(String baseJamuName) {
        this.baseJamuName = baseJamuName;
    }

    public Long getJamuId() {
        return jamuId;
    }

    public void setJamuId(Long jamuId) {
        this.jamuId = jamuId;
    }

    public Long getDose() {
        return dose;
    }

    public void setDose(Long dose) {
        this.dose = dose;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "DoseDTO{" +
                "id=" + id +
                ", baseJamuId=" + baseJamuId +
                ", baseJamuCode=" + baseJamuCode +
                ", baseJamuName='" + baseJamuName + '\'' +
                ", jamuId=" + jamuId +
                ", dose=" + dose +
                ", selected=" + selected +
                '}';
    }
}
