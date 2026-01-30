package com.burakcanaksoy.atomiccountryaggregator.model;

import lombok.Data;

@Data
public class Identity {
    private String isoCode;
    private String flagUrl;
    private String phoneCode;

    public Identity(String isoCode, String flagUrl, String phoneCode) {
        this.isoCode = isoCode;
        this.flagUrl = flagUrl;
        this.phoneCode = phoneCode;
    }
}
