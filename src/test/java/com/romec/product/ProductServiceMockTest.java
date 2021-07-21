package com.romec.product;

import com.romec.product.model.dao.ProductDao;
import com.romec.product.model.entity.CategoryEnt;
import com.romec.product.model.entity.ProductEnt;
import com.romec.product.model.service.ProductService;
import com.romec.product.model.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) //si no ejecuta el test agregar aesta linea
public class ProductServiceMockTest {

    @Mock
    private ProductDao productDao;

    private ProductService productService;

    @BeforeEach
    public void setup(){
        //MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productDao);
        ProductEnt computer = ProductEnt.builder()
                .name("computer")
                .categoryEnt(CategoryEnt.builder().id(1L).build())
                .stock(Double.parseDouble("5"))
                .price(Double.parseDouble("1233.99"))
                .build();
        Mockito.when(productDao.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productDao.save(computer)).thenReturn(computer);

    }
    @Test
    public void whenValidGetId_thenReturnProduct(){
        ProductEnt found = productService.getProductEnt(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }
    @Test
    public void whenValidUpdateStock_thenReturnNewStock(){
        ProductEnt newStock = productService.updateStock(1L,Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
    }
}
