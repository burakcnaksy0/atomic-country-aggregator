package com.burakcanaksoy.atomiccountryaggregator.service.impl;

import com.burakcanaksoy.atomiccountryaggregator.dto.CountryDetailsResponse;
import com.burakcanaksoy.atomiccountryaggregator.dto.CountryRequest;
import com.burakcanaksoy.atomiccountryaggregator.exception.ISONotFoundException;
import com.burakcanaksoy.atomiccountryaggregator.mapper.CountryDetailsMapper;
import com.burakcanaksoy.atomiccountryaggregator.service.CountryService;
import com.burakcanaksoy.countryaggregator.wsdl.CountryInfoServiceSoapType;
import com.burakcanaksoy.countryaggregator.wsdl.TCurrency;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryInfoServiceSoapType soapClient;
    private final CountryDetailsMapper countryDetailsMapper;

    public CountryServiceImpl(CountryInfoServiceSoapType soapClient, CountryDetailsMapper countryDetailsMapper) {
        this.soapClient = soapClient;
        this.countryDetailsMapper = countryDetailsMapper;
    }

    @Override
    public CountryDetailsResponse getCountryDetails(CountryRequest request) {
        String isoCode = soapClient.countryISOCode(request.getCountryName());

        if (isoCode == null || isoCode.length() != 2) {
            throw new ISONotFoundException("Girilen ülke ismiyle eşleşen bir kayıt bulunamadı (ISO kodu alınamadı).");
        }

        String capital = soapClient.capitalCity(isoCode);
        String flag = soapClient.countryFlag(isoCode);
        String phone = soapClient.countryIntPhoneCode(isoCode);
        TCurrency currency = soapClient.countryCurrency(isoCode);
        String language = soapClient.languageName(isoCode);

        return countryDetailsMapper.mapToCountryDetailsResponse(
                isoCode, capital, phone, language, currency, flag);

    }
}
