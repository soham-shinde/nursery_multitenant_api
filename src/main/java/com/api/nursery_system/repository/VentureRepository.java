package com.api.nursery_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.nursery_system.entity.Venture;

public interface VentureRepository extends JpaRepository<Venture, Long> {

}