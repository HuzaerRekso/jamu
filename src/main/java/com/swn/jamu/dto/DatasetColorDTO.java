package com.swn.jamu.dto;

public class DatasetColorDTO {

    private Long[] data;
    private String[] backgroundColor;

    public Long[] getData() {
        return data;
    }

    public void setData(Long[] data) {
        this.data = data;
    }

    public String[] getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String[] backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
