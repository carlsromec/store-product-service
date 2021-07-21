package com.romec.product.model.service;

import com.romec.product.model.dao.ProductDao;
import com.romec.product.model.entity.CategoryEnt;
import com.romec.product.model.entity.ProductEnt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    //la inyeccion de dependencia es atravez de constructor @RequiredArgsConstructor
    private final ProductDao productDao;

    @Override
    public List<ProductEnt> listProductEnt() {
        return productDao.findAll();
    }

    @Override
    public ProductEnt getProductEnt(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public ProductEnt createProductEnt(ProductEnt productEnt) {
        productEnt.setStatus("CREATED");
        productEnt.setCreateAt(new Date());
        return productDao.save(productEnt);
    }

    @Override
    public ProductEnt updateProductEnt(ProductEnt productEnt) {
        ProductEnt productDB = getProductEnt(productEnt.getId());
        if(null == productDB){
            return null;
        }
        productDB.setName(productEnt.getName());
        productDB.setDescription(productEnt.getDescription());
        productDB.setPrice(productEnt.getPrice());
        productDB.setCategoryEnt(productEnt.getCategoryEnt());
        productDB.setCreateAt(productEnt.getCreateAt());
        productDB.setStatus(productEnt.getStatus());
        return productDao.save(productEnt);
    }

    @Override
    public ProductEnt deleteProductEnt(Long id) {
        ProductEnt productDB = getProductEnt(id);
        if(null == productDB){
            return null;
        }
        productDB.setStatus("DELETED");
        return productDao.save(productDB);
    }

    @Override
    public List<ProductEnt> findByCategoryEnt(CategoryEnt categoryEnt) {
        return productDao.findByCategoryEnt(categoryEnt);
    }

    @Override
    public ProductEnt updateStock(Long id, Double quantity) {
        ProductEnt productDB = getProductEnt(id);
        if(null == productDB){
            return null;
        }
        Double stock = productDB.getStock()+quantity;
        productDB.setStock(stock);
        return productDao.save(productDB);
    }
}
