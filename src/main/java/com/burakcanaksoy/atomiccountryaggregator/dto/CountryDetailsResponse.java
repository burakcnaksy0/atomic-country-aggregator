package com.burakcanaksoy.atomiccountryaggregator.dto;

import com.burakcanaksoy.atomiccountryaggregator.model.*;
import lombok.Data;

@Data
public class CountryDetailsResponse {
    private Identity identity;
    private Geography geography;
    private Economy economy;
    private Culture culture;
}
