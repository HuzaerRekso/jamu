package com.swn.jamu.model;

import com.swn.jamu.base.BaseEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Jamu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private Long price;
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jamu")
    private List<Dose> dose;

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

    public List<Dose> getDose() {
        return dose;
    }

    public void setDose(List<Dose> dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "Jamu{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", active=" + active +
                ", dose=" + dose +
                '}';
    }
}
