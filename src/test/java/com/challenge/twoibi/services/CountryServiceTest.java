package com.challenge.twoibi.services;

import com.challenge.twoibi.domains.Country;
import com.challenge.twoibi.repositorys.CountryRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CountryServiceTest {

    @Autowired
    CountryServices countryServices;

    @MockBean
    CountryRepository repository;


    @Test
    public void test(){
        assertThat("hello").isEqualTo("hello");
    }

    @Test
    public void whenDeleteCountryThenCallRepositoryDelete(){
        Long id=1L;
        countryServices.deleteCountry(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void whenGetQueryListSizeThenReturnSix(){
        int queryListSize= countryServices.getQueryList().size();
        assertThat(queryListSize).isEqualTo(6);
    }

    @Test
    public void whenCallListOfCountriesThenCallRepoFindAll(){

        List<Country> countries=mockListOfCountries();
        when(repository.findAll()).thenReturn(countries);
        List<Country> list=countryServices.listOfCountries();
        verify(repository, times(1)).findAll();
        assertThat(list.get(1).getName()).isEqualTo("Mozambique");
    }

    @Test
    public void whenCallGetCountryThenCallRepoFindById(){
        Long id = 1L;
        Country country= mockListOfCountries().get(1);
        Optional<Country> countryOptional=Optional.of(country);
        when(repository.findById(id)).thenReturn(countryOptional);
        Country myCountry=countryServices.getCountry(id);
        verify(repository, times(1)).findById(id);
        assertThat(myCountry.getName()).isEqualTo(country.getName());

    }

    @Test
    public void whenCallSaveCountryThenCallRepoSave(){
        Country country= mockListOfCountries().get(1);
        Country country2= country;
        country2.setId(2L);
        when(repository.save(country)).thenReturn(country2);
        Country myCountry=countryServices.saveCountry(country);
        assertThat(myCountry).isEqualTo(country2);
        verify(repository, times(1)).save(any());

    }

    @Test
    public void whenCallUpdateCountryThenCallRepoSave(){
        Country country= mockListOfCountries().get(1);
        when(repository.save(country)).thenReturn(country);
        when(repository.findById(any())).thenReturn(Optional.of(country));
        Country myCountry=countryServices.updateCountry(country);
        when(repository.save(any())).thenReturn(null);
        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void whenSortedListOfCountriesThenCallRepoFindAll(){
        when(repository.findAll((Sort) any())).thenReturn(mockListOfCountries());
        List<Country> countries=countryServices.sortedListOfCountries("name");
        verify(repository,times(1)).findAll((Sort) any());
    }


    private List<Country> mockListOfCountries(){
        Country country= new Country.Builder()
                .name("Mozambique").capital("maputo").region("Africa").subRegion("Africa Austral").build();

        Country country2= new Country.Builder()
                .id(1L).name("Mozambique").capital("maputo").region("Africa").subRegion("Africa Austral").build();

        ArrayList<Country> countries= new ArrayList<>();
        countries.add(country);
        countries.add(country2);
        return countries;
    }
}
