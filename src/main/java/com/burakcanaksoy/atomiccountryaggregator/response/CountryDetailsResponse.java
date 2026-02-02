package com.burakcanaksoy.atomiccountryaggregator.response;

import com.burakcanaksoy.atomiccountryaggregator.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDetailsResponse {
    private Identity identity;
    private Geography geography;
    private Economy economy;
    private Culture culture;
}
