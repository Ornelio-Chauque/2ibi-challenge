package com.challenge.twoibi.services;

import com.challenge.twoibi.domains.Country;
import com.challenge.twoibi.repositorys.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CountryServices {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> listOfCountries(){
        return (List<Country>) countryRepository.findAll();
    }

    public List<Country> sortedListOfCountries(String sortAttribute){
        //countryRepository.findAll(Sort.by(sortAttribute));
        return null;
    }

    public Country getCountry(Long id){
       Optional<Country> optionalCountry= countryRepository.findById(id);
       return optionalCountry.orElse(null);
    }

    public Country saveCountry(Country country){
        return countryRepository.save(country);
    }

    public Country updateCountry(Country country){
        return countryRepository.save(country);
    }

    public void deleteCountry(Long id){
        countryRepository.deleteById(id);
    }

}
