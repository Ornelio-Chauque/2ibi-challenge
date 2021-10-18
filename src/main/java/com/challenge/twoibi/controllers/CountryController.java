package com.challenge.twoibi.controllers;

import com.challenge.twoibi.domains.Country;
import com.challenge.twoibi.dtos.CountryDTO;
import com.challenge.twoibi.dtos.CountryTransformer;
import com.challenge.twoibi.services.CountryServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    private final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    CountryServices countryServices;

    @Autowired
    CountryTransformer transformer;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity CountryList() {
        Set<Country> listOfCountries = new HashSet<>();
        Country country1 = new Country.Builder()
                .id(1L)
                .name("Mozambique")
                .capital("Maputo")
                .region("Africa")
                .subRegion("Autral Africa")
                .area(3000000)
                .build();

        Country country2 = new Country.Builder()
                .id(2L)
                .name("Portugal")
                .capital("Lisboa")
                .region("Europe")
                .subRegion("East Europo")
                .area(30000)
                .build();
        listOfCountries.add(country1);
        listOfCountries.add(country2);


        Set<CountryDTO> countryDTOS = new HashSet<>();
        CountryTransformer mapper = new CountryTransformer();
        for (Country country : listOfCountries) {
            countryDTOS.add(mapper.toDto(country));
        }

        return ResponseEntity.status(HttpStatus.OK).body(countryDTOS);
    }

    @GetMapping(value = "", params = {"sortBy=name", "asc=true"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity SortedCountryList(@RequestParam("sortBy") String sortBy, @RequestParam(value = "asc", required = false, defaultValue = "true") boolean asc) {
        LOGGER.info("The request params are: " + "SortBy= " + sortBy + " and asc=" + asc);

        Country country1 = new Country.Builder()
                .id(1L)
                .name("Mozambique")
                .capital("Maputo")
                .region("Africa")
                .subRegion("Autral Africa")
                .area(300.000)
                .build();
        CountryTransformer mapper = new CountryTransformer();
        CountryDTO countryDTO = mapper.toDto(country1);
        return ResponseEntity.status(HttpStatus.OK).body(country1);
    }

    @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCountry(@PathVariable("id") Long id) {

        Country country1 = new Country.Builder()
                .id(1L)
                .name("Mozambique")
                .capital("Maputo")
                .region("Africa")
                .subRegion("Autral Africa")
                .area(300.000)
                .build();
        CountryTransformer mapper = new CountryTransformer();
        CountryDTO countryDTO = mapper.toDto(country1);
        return ResponseEntity.status(HttpStatus.OK).body(countryDTO);
    }


    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity deleteCountry(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveCountry(@RequestBody CountryDTO country) {
        LOGGER.info("Your country data is"+ country);

        Country country1 = transformer.toEntity(country);
        country1.setId(3L);
        CountryDTO countryDTO = transformer.toDto(country1);
        return ResponseEntity.status(HttpStatus.CREATED).body(countryDTO);
    }


    @PutMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCountry(@RequestBody CountryDTO countryDTO) {

        Country country = transformer.toEntity(countryDTO);
        country.setId(4L);
        CountryDTO countryDTO1 = transformer.toDto(country);
        return ResponseEntity.status(HttpStatus.OK).body(countryDTO1);
    }
}
