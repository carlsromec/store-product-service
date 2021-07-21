package com.romec.product.model.dao;

import com.romec.product.model.entity.CategoryEnt;
import com.romec.product.model.entity.ProductEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductDao extends JpaRepository<ProductEnt, Long> {
    public List<ProductEnt> findByCategoryEnt(CategoryEnt categoryEnt);
}
