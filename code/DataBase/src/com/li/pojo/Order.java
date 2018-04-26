package com.li.pojo;

import java.io.Serializable;
/*应导入此包下面的Date*/
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 7661403309178948735L;
	
	private Long id;
	private Date orderedDate;
	private Date shippedDate;
	private Double total;
	/*存储当前订单所有的订单明细*/
	private List<OrderLine> orderlines = new ArrayList<OrderLine>();
	
	public Order() {}
	
	public Order(Long id, Date orderedDate, Date shippedDate, Double total, List<OrderLine> orderlines ) {
		super();
		this.id = id;
		this.orderedDate = orderedDate;
		this.shippedDate = shippedDate;
		this.total = total;
		this.orderlines=orderlines;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Date getshippedDate() {
		return shippedDate;
	}

	public void setshippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<OrderLine> getOrderlines() {
		return orderlines;
	}

	public void setOrderlines(List<OrderLine> orderlines) {
		this.orderlines = orderlines;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderedDate=" + orderedDate + ", shippedDate=" + shippedDate + ", total=" + total
				+ "]";
	}

}
