package com.challenge.twoibi.repositorys;

import com.challenge.twoibi.domains.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
}
