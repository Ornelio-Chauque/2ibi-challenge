package com.challenge.twoibi.controllers;

import com.challenge.twoibi.domains.Country;
import com.challenge.twoibi.dtos.CountryDTO;
import com.challenge.twoibi.dtos.CountryTransformer;
import com.challenge.twoibi.services.CountryServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL_LONG;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CountryServices services;

    @Test
    public void whenGetThenReturnListOfDto() throws Exception {

        when(services.listOfCountries()).thenReturn(mockListOfCountries());
        //when(transformer.toDto(any())).thenReturn(mockListOfCountriesDto().get(1));
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/country"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].capital", is("maputo")))
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
    }

    @Test
    public void whenGetByIdThenReturnCountryDTO() throws Exception {
        when(services.getCountry(any())).thenReturn(mockListOfCountries().get(0));
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/country/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.region", is("Africa")))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("maputo");

    }

    @Test
    public void whenCountryNotFoundThen400() throws Exception {
        when(services.getCountry(any())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/country/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void whenDeleteValidCountryThenReturn200() throws Exception {
        when(services.getCountry(any())).thenReturn(mockListOfCountries().get(0));
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/country/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(services, times(1)).deleteCountry(any());
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    public void whenDeleteInvalidCountryThenReturn400() throws Exception {
        when(services.getCountry(any())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/country/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    public void whenSaveCountryThenReturnCreated() throws Exception {
        when(services.saveCountry(any())).thenReturn(mockListOfCountries().get(0));
        String requestBody = "{\"name\":\"Mozambique\",\"capital\":\"maputo\",\"region\":\"Africa\",\"subRegion\":\"Africa Austral\",\"area\":\"30,000 m2\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/country")
                .contentType(MediaType.APPLICATION_JSON.toString())
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
        assertThat(mvcResult.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());

    }

    @Test
    public void whenUpdateValidCountryThenReturn200() throws Exception {
        String requestBody = "{\"name\":\"Mozambique\",\"capital\":\"maputo\",\"region\":\"Africa\",\"subRegion\":\"Africa Austral\",\"area\":\"30,000 m2\"}";
        when(services.getCountry(any())).thenReturn(mockListOfCountries().get(0));
        when(services.updateCountry(any())).thenReturn(mockListOfCountries().get(0));
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/country/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON.toString())
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capital", is("maputo")))
                .andExpect(jsonPath("$.name", is("Mozambique")))
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
    }

    @Test
    public void whenUpdateInvalidCountryThenReturn404() throws Exception {
        String requestBody = "{\"name\":\"Mozambique\",\"capital\":\"maputo\",\"region\":\"Africa\",\"subRegion\":\"Africa Austral\",\"area\":\"30,000 m2\"}";
        when(services.getCountry(any())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/country/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON.toString())
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    private List<Country> mockListOfCountries() {
        Country country = new Country.Builder()
                .id(2L).name("Mozambique").capital("maputo").region("Africa").subRegion("Africa Austral").area(30000).build();

        Country country2 = new Country.Builder()
                .id(1L).name("Mozambique").capital("maputo").region("Africa").subRegion("Africa Austral").area(2000).build();

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(country);
        countries.add(country2);
        return countries;
    }

    private List<CountryDTO> mockListOfCountriesDto() {
        CountryDTO country = new CountryDTO.Builder()
                .name("Mozambique").capital("maputo").region("Africa").subRegion("Africa Austral").build();

        CountryDTO country2 = new CountryDTO.Builder()
                .id(1L).name("Mozambique").capital("maputo").region("Africa").subRegion("Africa Austral").build();

        ArrayList<CountryDTO> countries = new ArrayList<>();
        countries.add(country);
        countries.add(country2);
        return countries;
    }
}
