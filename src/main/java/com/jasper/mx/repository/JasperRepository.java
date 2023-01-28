package com.jasper.mx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jasper.mx.model.entity.Main;

@Repository
public interface JasperRepository extends JpaRepository<Main, Integer> {

}
