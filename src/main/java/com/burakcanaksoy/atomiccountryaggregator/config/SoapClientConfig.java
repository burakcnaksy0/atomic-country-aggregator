package com.burakcanaksoy.atomiccountryaggregator.config;

import com.burakcanaksoy.countryaggregator.wsdl.CountryInfoService;
import com.burakcanaksoy.countryaggregator.wsdl.CountryInfoServiceSoapType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapClientConfig {
    @Bean
    public CountryInfoServiceSoapType countryInfoServiceSoapType() {
        CountryInfoService service = new CountryInfoService();
        return service.getCountryInfoServiceSoap();
    }
}