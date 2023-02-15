package com.zeusbe.repository;

import com.zeusbe.model.AppConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigurationRepository extends JpaRepository<AppConfiguration, Long> {
    AppConfiguration findByKey(String key);
}
