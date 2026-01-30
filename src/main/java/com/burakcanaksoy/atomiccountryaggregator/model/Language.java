package com.burakcanaksoy.atomiccountryaggregator.model;

import lombok.Data;

@Data
public class Language {
    private String isoCode;
    private String name;

    public Language(String isoCode, String name) {
        this.isoCode = isoCode;
        this.name = name;
    }
}
