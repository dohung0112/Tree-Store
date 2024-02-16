package com.datamining.dao;

import com.datamining.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
	
    @Query(value="SELECT * FROM products WHERE categories_id=?1", nativeQuery = true)
    List<Product> findByCategoryId(String cid);


    Product findByUrlEquals(String url);

    @Query(value="SELECT * FROM Products p WHERE p.name like %?1%", nativeQuery = true)
    List<Product> findByKeyword(String keyword);


    //find product bettwen two price
    @Query(value="select DISTINCT  p.* from Products p left join Product_Size ps on p.id = ps.product_id  join categories c on p.categories_id = c.id where (p.price >= ?1 and p.price <= ?2)  or (ps.price >=  ?1 and ps.price <= ?2)  and c.url = ?3 order by p.price asc;\n", nativeQuery = true)
    List<Product> findByPriceBetweenByCate(Double price1, Double price2, String url);

    @Query(value="select DISTINCT p.* from Products p left join Product_Size ps on p.id = ps.product_id where (p.price >= ?1 and p.price <= ?2)  or (ps.price >=  ?1 and ps.price <= ?2) order by p.price asc", nativeQuery = true)
    List<Product> findByPriceBetween(Double price1, Double price2);

    // Top 5 seller
    @Query(value="select p.* from Order_Detail d join Products p on p.id = d.product_id  group by p.id order by count(p.id) DESC LIMIT 5", nativeQuery = true)
    List<Product> findTop5Seller();
    
    @Query(value="select p.* from products p left join product_rate pr on p.id = pr.product_id group by p.id order by avg(rate) desc, count(*) desc", nativeQuery = true)
    List<Product> selectAllFeedbacks();


    // page

    @Query(value="SELECT * FROM products WHERE categories_id=?1", nativeQuery = true)
    Page<Product> findByCategoryIdByPage(String cid, Pageable pageable);

    @Query(value="SELECT * FROM products WHERE name like %?1%", nativeQuery = true)
    Page<Product> findByKeywordPage(String keyword, Pageable pageable);


    //find product bettwen two price
    @Query(value="select * from Products where (price >= ?1 and price <= ?2) "
            + "order by price asc", nativeQuery = true)
    Page<Product> findByPriceBetweenPage(Double price1, Double price2, Pageable pageable);

    @Query(value="select DISTINCT  p.* from Products p left join Product_Size ps on p.id = ps.product_id " +
            " join categories c on p.categories_id = c.id where (p.price >= ?1 and p.price <= ?2) " +
            " or (ps.price >=  ?1 and ps.price <= ?2)  and c.url = ?3 order by p.price asc", nativeQuery = true)
    List<Product> findByPriceBetweenByCate(Double price1, Double price2, String url, Pageable pageable);


    // dashbroad

    @Query(value = "select p.*,SUM(d.quantity),SUM(d.quantity * p.price) from order_detail d join products p on p.id = d.product_id" +
            " group by p.id order by SUM(d.quantity) DESC LIMIT 5",nativeQuery = true)
    List<Product> top5Sale();
}
