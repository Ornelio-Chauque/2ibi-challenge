package com.challenge.twoibi.transformers;

import com.challenge.twoibi.domains.Country;
import com.challenge.twoibi.dtos.CountryDTO;
import com.challenge.twoibi.dtos.CountryTransformer;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Entity;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTransformers {

    private static CountryTransformer transformer;

    @BeforeAll
    public static void init(){
        transformer= new CountryTransformer();
    }

    @Test
    public void givenDtoThenReturnEntity() {
        CountryDTO countryDTO = new CountryDTO.Builder()
                .id(1L)
                .name("Mozambique")
                .capital("Maputo")
                .region("Africa")
                .subRegion("Africa Austral")
                .area("32000 m2")
                .build();
        assertThat(transformer.toEntity(countryDTO)).isInstanceOf(Country.class);

    }

    @Test
    public void givenEntityThenReturnDto() {
        Country country = new Country.Builder()
                .id(1L)
                .name("Mozambique")
                .capital("Maputo")
                .region("Africa")
                .subRegion("Africa Austral")
                .area(320000)
                .build();
        assertThat(transformer.toDto(country)).isInstanceOf(CountryDTO.class);

    }
}
