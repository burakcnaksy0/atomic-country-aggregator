package com.burakcanaksoy.atomiccountryaggregator.model;

import lombok.Data;

import java.util.List;

@Data
public class Culture {
    private String isoCode;
    private String languages;

    public Culture(String isoCode,String languages) {
        this.isoCode = isoCode;
        this.languages = languages;
    }
}
