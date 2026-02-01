package com.burakcanaksoy.atomiccountryaggregator.controller;

import com.burakcanaksoy.atomiccountryaggregator.dto.ApiResponse;
import com.burakcanaksoy.atomiccountryaggregator.dto.CountryDetailsResponse;
import com.burakcanaksoy.atomiccountryaggregator.dto.CountryRequest;
import com.burakcanaksoy.atomiccountryaggregator.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/details")
    public ResponseEntity<ApiResponse<CountryDetailsResponse>> getDetails(@RequestBody CountryRequest request) {
        CountryDetailsResponse data = countryService.getCountryDetails(request);
        return ResponseEntity.ok(ApiResponse.success("Country information was successfully retrieved.", data));
    }
}
