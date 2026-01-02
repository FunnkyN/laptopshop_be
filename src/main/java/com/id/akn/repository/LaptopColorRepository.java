package com.id.akn.repository;

import com.id.akn.model.Color;
import com.id.akn.model.Laptop;
import com.id.akn.model.LaptopColor;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LaptopColorRepository extends JpaRepository<LaptopColor, Integer> {

    @Query(value = "SELECT * FROM laptop_colors WHERE laptop_id = :laptopId LIMIT 1", nativeQuery = true)
    LaptopColor findByLaptopId(@Param("laptopId") Integer laptopId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LaptopColor> findByLaptopAndColor(Laptop laptop, Color color);
}
