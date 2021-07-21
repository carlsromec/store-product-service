package com.romec.product.model.service;

import com.romec.product.model.entity.CategoryEnt;
import com.romec.product.model.entity.ProductEnt;

import java.util.List;

public interface ProductService {
    public List<ProductEnt> listProductEnt();
    public ProductEnt getProductEnt(Long id);

    public ProductEnt createProductEnt(ProductEnt productEnt);
    public ProductEnt updateProductEnt(ProductEnt productEnt);
    public ProductEnt deleteProductEnt(Long id);
    public List<ProductEnt> findByCategoryEnt(CategoryEnt categoryEnt);
    public ProductEnt updateStock(Long id,Double quantity);
}
