package com.challenge.twoibi.dtos;

import com.challenge.twoibi.domains.Country;
import org.springframework.stereotype.Service;

@Service
public class CountryTransformer {
    private final String NO_AREA_DATA = "No area information supplied";

    public CountryTransformer() {
    }

    public CountryDTO toDto(Country country) {
        CountryDTO countryDTO = new CountryDTO.Builder()
                .id(country.getId())
                .name(country.getName())
                .capital(country.getCapital())
                .region(country.getRegion())
                .subRegion(country.getSubRegion())
                .area(formatArea(country.getArea()))
                .build();

        return countryDTO;
    }

    public Country toEntity(CountryDTO countryDTO) {
        Country country = new Country.Builder()
                .name(countryDTO.getName())
                .capital(countryDTO.getCapital())
                .region(countryDTO.getRegion())
                .subRegion(countryDTO.getSubRegion())
                .area(parseArea(countryDTO.getArea()))
                .build();
        return country;
    }

    private String formatArea(Double countryArea) {
        return countryArea != null && countryArea != 0 ? String.format("%,.0f m2", countryArea) : NO_AREA_DATA;
    }

    private Double parseArea(String area){
        if(area==null) return 0D;
        else return area.equals("")?0D: Double.valueOf(area);

    }
}
