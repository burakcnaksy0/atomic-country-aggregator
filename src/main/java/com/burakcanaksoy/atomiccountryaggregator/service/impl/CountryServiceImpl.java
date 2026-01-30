package com.burakcanaksoy.atomiccountryaggregator.service.impl;

import com.burakcanaksoy.atomiccountryaggregator.dto.CountryDetailsResponse;
import com.burakcanaksoy.atomiccountryaggregator.dto.CountryRequest;
import com.burakcanaksoy.atomiccountryaggregator.exception.ISONotFoundException;
import com.burakcanaksoy.atomiccountryaggregator.model.*;
import com.burakcanaksoy.atomiccountryaggregator.service.CountryService;
import com.burakcanaksoy.countryaggregator.wsdl.CountryInfoServiceSoapType;
import com.burakcanaksoy.countryaggregator.wsdl.TCurrency;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryInfoServiceSoapType soapClient;

    public CountryServiceImpl(CountryInfoServiceSoapType soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public CountryDetailsResponse getCountryDetails(CountryRequest request) {q  
        String isoCode = soapClient.countryISOCode(request.getCountryName());

        if (isoCode == null || isoCode.length() != 2) {
            throw new ISONotFoundException("Girilen ülke ismiyle eşleşen bir kayıt bulunamadı (ISO kodu alınamadı).");
        }

        String capitalFuture = soapClient.capitalCity(isoCode);
        String flagFuture = soapClient.countryFlag(isoCode);
        String phoneFuture = soapClient.countryIntPhoneCode(isoCode);
        TCurrency currencyFuture = soapClient.countryCurrency(isoCode);
        String languageFuture = soapClient.languageName(isoCode);

        CountryDetailsResponse response = new CountryDetailsResponse();

        response.setIdentity(new Identity(isoCode, flagFuture, phoneFuture));
        response.setGeography(new Geography(capitalFuture));
        response.setEconomy(new Economy(currencyFuture.getSISOCode(), currencyFuture.getSName()));
        response.setCulture(new Culture(isoCode, languageFuture));

        return response;

    }
}
