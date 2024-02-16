package com.datamining.entity;

import com.datamining.DTO.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.var;
import org.springframework.security.core.Transient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Products")
@JsonIgnoreProperties({"likes", "productRates"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@NotNull
	private Double price;
	@NotNull
	private Integer quantity;
	@NotNull
	private String image;
	private String description;
	private Boolean status;
	private String url;
	private Boolean is_delete = false;
	@Temporal(TemporalType.DATE)
	private Date create_date = new Date();
	@Temporal(TemporalType.DATE)
	private Date update_date = new Date();

	// discount_id
	@ManyToOne
	@JoinColumn(name = "discount_id")
	private Discount discount;

	// material_id
	@ManyToOne
	@JoinColumn(name = "prod_material_id")
	ProductMaterial material;

	// categories_id
	@ManyToOne
	@JoinColumn(name = "categories_id")
	private Category categories;

	// Wish_List
	@ManyToMany(mappedBy = "likedProducts", fetch = FetchType.LAZY)
	private List<Account> likes;

	// Order_Detail
	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<OrderDetail> oderDetails;

//	// Product_Rate
//	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
//	private List<ProductRate> productRates;

	// Product_Size
	@JsonManagedReference
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductSize> productSizes;

	public Product() {

	}


	public static Product convert(ProductDTO productDTO) {
		// TODO Auto-generated method stub
		var product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setQuantity(productDTO.getQuantity());
		product.setImage(productDTO.getImage());
		product.setUrl(productDTO.getUrl());
		product.setDescription(productDTO.getDescription());
		product.setCreate_date(productDTO.getCreatedDate());
		product.setUpdate_date(productDTO.getUpdatedDate());
		product.setCategories(productDTO.getCategories());
		product.setStatus(productDTO.getStatus());
		return product;
	}
}
