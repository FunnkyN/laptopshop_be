package com.id.akn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.id.akn.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Byte> {
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name)")
    Category findByNameNormalize(@Param("name") String name);
}
