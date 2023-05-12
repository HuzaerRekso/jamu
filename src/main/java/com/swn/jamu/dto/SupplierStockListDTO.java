package com.swn.jamu.dto;

import java.util.List;

public class SupplierStockListDTO {
    private List<SupplierStockDTO> stocks;

    public List<SupplierStockDTO> getStocks() {
        return stocks;
    }

    public void setStocks(List<SupplierStockDTO> stocks) {
        this.stocks = stocks;
    }
}
