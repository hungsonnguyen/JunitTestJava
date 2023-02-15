package com.zeusbe.repository;

import com.zeusbe.model.Nationality;
import com.zeusbe.model.Province;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Province entity.
 */
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findByNationality(Nationality nationality);
}
