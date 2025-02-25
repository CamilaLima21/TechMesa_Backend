package com.fiap.techmesa.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techmesa.infrastructure.persistence.entity.RatingEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long>{

}
