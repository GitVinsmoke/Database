package com.li.pojo;

import java.io.Serializable;

public class OrderLine implements Serializable {

	private static final long serialVersionUID = 3097960437287859729L;
	
	private Long id;
	private Double price;
	private Long quantity;
	private String product;
	/*通过订单明细可以访问订单表*/
	private Order order;
	
	public OrderLine() {}
	
	public OrderLine(Long id, Double price, Long quantity, String product, Order order) {
		super();
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.product = product;
		this.order=order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderLine [id=" + id + ", price=" + price + ", quantity=" + quantity + ", product=" + product + "]";
	}
	
}
