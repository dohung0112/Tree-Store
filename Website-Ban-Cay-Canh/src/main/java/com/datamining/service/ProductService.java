package com.datamining.service;

import com.datamining.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    List<Product> findAll();

    Product findById(Integer id);

    Product findByUrlEquals(String url);

    List<Product> findByCategoryId(String cid);

    List<Product> findByKeyword(String keyword);


    List<Product> findByPriceBetweenByCate(Double price1, Double price2, String url);

//    List<Product> findByPriceBetween(Double price1, Double price2);

    Product create(Product product);

    Product update(Integer id, Product product);

    void delete(Integer id);

    List<Product> findTop5Seller();
    
    List<Product> selectAllFeedbacks();

    // page

    Page<Product> findAllByPage(Pageable page);

    Page<Product> findByCategoryIdByPage(String cid,Pageable page);

    Page<Product> findByKeywordPage(String keyword, Pageable pageable);

    Page<Product> findByPriceBetweenPage(Double price1, Double price2, Pageable pageable);

//    List<Product> findByPriceBetweenByCate(Double price1, Double price2, String url, Pageable pageable);

    //dashbroad

    List<Product> top5Sale();
}
