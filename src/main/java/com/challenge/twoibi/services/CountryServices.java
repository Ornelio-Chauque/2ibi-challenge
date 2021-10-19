package com.challenge.twoibi.services;

import com.challenge.twoibi.domains.Country;
import com.challenge.twoibi.repositorys.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CountryServices {
    private List<String> queryList = Arrays.asList("name", "id", "region", "subregion", "capital", "area");
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> listOfCountries() {
        return countryRepository.findAll();
    }

    public List<Country> sortedListOfCountries(String sortAttribute) {
        List<Country> countries = countryRepository.findAll(Sort.by(Sort.Direction.ASC, sortAttribute));
        return countries;
    }

    public Country getCountry(Long id) {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        return optionalCountry.orElse(null);
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Country country) {
        Country myCountry = countryRepository.findById(country.getId()).orElse(null);

        myCountry.setName(country.getName());
        myCountry.setArea(country.getArea());
        myCountry.setCapital(country.getCapital());
        myCountry.setRegion(country.getRegion());
        myCountry.setSubRegion(country.getSubRegion());
        return countryRepository.save(myCountry);
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }

    public List<String> getQueryList() {
        return this.queryList;
    }

}
