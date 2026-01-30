package com.burakcanaksoy.atomiccountryaggregator.service;

import com.burakcanaksoy.atomiccountryaggregator.dto.CountryDetailsResponse;
import com.burakcanaksoy.atomiccountryaggregator.dto.CountryRequest;

public interface CountryService {
    CountryDetailsResponse getCountryDetails(CountryRequest request);
}
