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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Country> countries= countryServices.listOfCountries();
        List<CountryDTO> countryDTOS=countries.stream().map(transformer::toDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(countryDTOS);
    }

    @GetMapping(value = "", params = "sortBy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity SortedCountryList(@RequestParam("sortBy") String sortBy) {
        LOGGER.info("The request params are: " + "SortBy= " + sortBy);
        List<Country> countries= countryServices.sortedListOfCountries(sortBy);
        List<CountryDTO> countriesDto= countries.stream().map(transformer::toDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(countriesDto);
    }

    @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCountry(@PathVariable("id") Long id) {

        Country country = countryServices.getCountry(id);
        if(country==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        CountryDTO countryDTO = transformer.toDto(country);
        return ResponseEntity.status(HttpStatus.OK).body(countryDTO);
    }


    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity deleteCountry(@PathVariable("id") Long id) {
        Country country=countryServices.getCountry(id);
        if(country==null)  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        countryServices.deleteCountry(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveCountry(@RequestBody CountryDTO countryDTO) {
        LOGGER.info("Your country data is"+ countryDTO);

        Country country=countryServices.saveCountry(transformer.toEntity(countryDTO));

        CountryDTO myCountryDTO = transformer.toDto(country);
        return ResponseEntity.status(HttpStatus.CREATED).body(myCountryDTO);
    }


    @PutMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCountry(@RequestBody CountryDTO countryDTO, @PathVariable("id") Long id)  {

        if(countryServices.getCountry(id)==null) return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Country myCountry = transformer.toEntity(countryDTO);
        Country country=countryServices.updateCountry(myCountry);
        CountryDTO myCountryDTO = transformer.toDto(country);
        return ResponseEntity.status(HttpStatus.OK).body(myCountryDTO);
    }
}
