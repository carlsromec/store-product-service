package com.romec.product.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

}
