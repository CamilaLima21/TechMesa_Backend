package com.fiap.techmesa.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techmesa.infrastructure.persistence.entity.OpeningHoursEntity;

public interface OpeningHoursRepository extends JpaRepository<OpeningHoursEntity, Long>{

}
