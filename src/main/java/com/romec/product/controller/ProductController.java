package com.romec.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romec.product.model.entity.CategoryEnt;
import com.romec.product.model.entity.ErrorMessage;
import com.romec.product.model.entity.ProductEnt;
import com.romec.product.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductEnt>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<ProductEnt> products = new ArrayList<>();
        if(null == categoryId){
            products = productService.listProductEnt();
            if(products.isEmpty()){
                return  ResponseEntity.noContent().build();
            }
        }else {
            products =productService.findByCategoryEnt(CategoryEnt.builder().id(categoryId).build());
            if(products.isEmpty()){
                return  ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductEnt> getProduct(@PathVariable("id") Long id){
        ProductEnt productEnt = productService.getProductEnt(id);
        if(null==productEnt){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productEnt);
    }

    @PostMapping
    public ResponseEntity<ProductEnt> createProduct(@Valid @RequestBody ProductEnt productEnt, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        ProductEnt createproduct = productService.createProductEnt(productEnt);
        return ResponseEntity.status(HttpStatus.CREATED).body(createproduct);
    }

    @PutMapping(value = "/{id}")
    public  ResponseEntity<ProductEnt> updateProduct(@PathVariable("id") Long id, @RequestBody ProductEnt productEnt){
        productEnt.setId(id);
        ProductEnt productDB = productService.updateProductEnt(productEnt);
        if(null==productDB){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductEnt> deleteProduct(@PathVariable("id") Long id){
        ProductEnt deleteproduct = productService.deleteProductEnt(id);
        if(null == deleteproduct){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deleteproduct);
    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<ProductEnt> updateStock(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity){
        ProductEnt updatestock = productService.updateStock(id,quantity);
        if(null == updatestock){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatestock);
    }

    //structure the message

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}
