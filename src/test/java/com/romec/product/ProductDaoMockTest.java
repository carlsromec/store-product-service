package com.romec.product;

import com.romec.product.model.dao.ProductDao;
import com.romec.product.model.entity.CategoryEnt;
import com.romec.product.model.entity.ProductEnt;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) //si no ejecuta el test agregar aesta linea
public class ProductDaoMockTest {
    @Autowired
    private ProductDao productDao;
    @Test
    public void whenFindByCategory_thenReturnListProduct(){
        ProductEnt product01 = ProductEnt.builder()
                .name("computer")
                .categoryEnt(CategoryEnt.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("5"))
                .price(Double.parseDouble("1233.99"))
                .status("created")
                .createAt(new Date()).build();

        productDao.save(product01);
        List<ProductEnt> builds= productDao.findByCategoryEnt(product01.getCategoryEnt());
        Assertions.assertThat(builds.size()).isEqualTo(9);
    }
}
