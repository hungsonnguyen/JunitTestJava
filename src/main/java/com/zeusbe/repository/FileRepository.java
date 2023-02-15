package com.zeusbe.repository;

import com.zeusbe.model.File;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the File entity.
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {}
