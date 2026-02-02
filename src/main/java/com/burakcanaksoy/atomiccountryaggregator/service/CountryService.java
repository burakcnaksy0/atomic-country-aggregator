package com.burakcanaksoy.atomiccountryaggregator.service;

import com.burakcanaksoy.atomiccountryaggregator.response.CountryDetailsResponse;
import com.burakcanaksoy.atomiccountryaggregator.request.CountryRequest;

public interface CountryService {
    CountryDetailsResponse getCountryDetails(CountryRequest request);
}
