package com.zeusbe.repository;

import com.zeusbe.model.City;
import com.zeusbe.model.Province;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByProvince(Province province);
}
