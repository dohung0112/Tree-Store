package com.datamining.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

import com.datamining.entity.Order;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDAO extends JpaRepository<Order, Integer> {
	
	@Query(value="SELECT * FROM Orders o JOIN Order_Status os ON o.orderstatus_id = os.id WHERE os.name = 'Chờ xác nhận' "
			+ "or os.name = 'Đã xác nhận' or os.name = 'Chuẩn bị hàng' or os.name = 'Đang giao' order by os.id", nativeQuery = true)
    List<Order> findAllOrders();
	
	@Query(value="SELECT * FROM Orders o JOIN Order_Status os ON o.orderstatus_id = os.id WHERE os.name = 'Đã nhận'", nativeQuery = true)
    List<Order> findAllByCompleted();
	
	@Query(value="SELECT * FROM Orders o JOIN Order_Status os ON o.orderstatus_id = os.id WHERE os.name = 'Đã hủy'", nativeQuery = true)
    List<Order> findAllByCanceled();
	
	@Query(value="SELECT * FROM Orders o JOIN Order_Status os ON o.orderstatus_id = os.id WHERE os.id = ?1", nativeQuery = true)
    List<Order> findAllByFilter(Integer idStatus);
	
	@Query(value="SELECT * FROM Orders o JOIN Order_Status os ON o.orderstatus_id = os.id WHERE o.create_date like ?1% AND (os.id = 1 OR os.id = 2 OR os.id = 3 OR os.id = 4)", nativeQuery = true)
    List<Order> findAllByFilter(String createDate);
	
	@Query(value="SELECT * FROM Orders o JOIN Order_Status os ON o.orderstatus_id = os.id WHERE os.id = ?1 AND o.create_date like ?2%", nativeQuery = true)
    List<Order> findAllByFilter(Integer idStatus,String createDate);
	
	@Query(value="SELECT * FROM Orders WHERE profile_id = ?1", nativeQuery = true)
    List<Order> findByProfileId(Integer idProfile);
	
	@Query(value="SELECT * FROM Orders o JOIN Profile pf on o.profile_id = pf.id WHERE o.id = ?1 or pf.fullname like %?1%", nativeQuery = true)
    List<Order> findByKeyWord(String keyword);
	
	@Query(value="SELECT SUM(quantity) FROM Orders o JOIN Order_Detail od on o.id = od.order_id WHERE create_date BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') AND LAST_DAY(NOW())", nativeQuery = true)
    Integer getTotalProductSold();
	
}
