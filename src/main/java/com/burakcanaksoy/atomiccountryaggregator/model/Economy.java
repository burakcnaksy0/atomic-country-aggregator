package com.burakcanaksoy.atomiccountryaggregator.model;

import lombok.Data;

@Data
public class Economy {
    private String currencyCode;
    private String currencyName;

    public Economy(String currencyCode, String currencyName) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }
}
