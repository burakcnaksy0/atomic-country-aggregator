package com.burakcanaksoy.atomiccountryaggregator.model;

import lombok.Data;

@Data
public class Geography {
    private String capital;

    public Geography(String capital) {
        this.capital = capital;
    }
}
