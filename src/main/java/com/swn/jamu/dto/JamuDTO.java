package com.swn.jamu.dto;

import java.util.List;

public class JamuDTO {

    private Long id;
    private String code;
    private String name;
    private Long price;
    private Boolean active;
    private List<DoseDTO> dose;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<DoseDTO> getDose() {
        return dose;
    }

    public void setDose(List<DoseDTO> dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "JamuDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", active=" + active +
                ", dose=" + dose +
                '}';
    }
}
