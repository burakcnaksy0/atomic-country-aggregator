package com.burakcanaksoy.atomiccountryaggregator.mapper;

import com.burakcanaksoy.atomiccountryaggregator.dto.CountryDetailsResponse;
import com.burakcanaksoy.atomiccountryaggregator.model.Culture;
import com.burakcanaksoy.atomiccountryaggregator.model.Economy;
import com.burakcanaksoy.atomiccountryaggregator.model.Geography;
import com.burakcanaksoy.atomiccountryaggregator.model.Identity;
import com.burakcanaksoy.countryaggregator.wsdl.TCurrency;
import org.springframework.stereotype.Component;

@Component
public class CountryDetailsMapper {

    public CountryDetailsResponse mapToCountryDetailsResponse(
            String isoCode,
            String capital,
            String phone,
            String language,
            TCurrency currency,
            String flag) {
        return new CountryDetailsResponse(
                mapIdentity(isoCode, flag, phone),
                mapGeography(capital),
                mapEconomy(currency),
                mapCulture(isoCode, language));
    }

    private Identity mapIdentity(String isoCode, String flag, String phone) {
        return new Identity(isoCode, flag, phone);
    }

    private Geography mapGeography(String capital) {
        return new Geography(capital);
    }

    private Economy mapEconomy(TCurrency currency) {
        return new Economy(currency.getSISOCode(), currency.getSName());
    }

    private Culture mapCulture(String isoCode, String language) {
        return new Culture(isoCode, language);
    }
}
