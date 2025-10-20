package com.id.akn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.id.akn.model.Laptop;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Integer>, JpaSpecificationExecutor<Laptop> {

    @Query("SELECT l FROM Laptop l WHERE LOWER(l.model) LIKE LOWER(CONCAT('%', :model, '%'))")
    List<Laptop> searchLaptop(@Param("model") String model);

    @Query("SELECT l FROM Laptop l ORDER BY l.discountPercent DESC")
    List<Laptop> findTop10ByHighestDiscount();

    @Query(value = "SELECT COUNT(*) FROM laptops", nativeQuery = true)
    Long getTotalProduct();
    boolean existsLaptopByModel(String model);
}
