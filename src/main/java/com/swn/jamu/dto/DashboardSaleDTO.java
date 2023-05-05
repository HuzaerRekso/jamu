package com.swn.jamu.dto;

public class DashboardSaleDTO {

    private String[] labels;
    private DatasetColorDTO datasets;

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public DatasetColorDTO getDatasets() {
        return datasets;
    }

    public void setDatasets(DatasetColorDTO datasets) {
        this.datasets = datasets;
    }
}
