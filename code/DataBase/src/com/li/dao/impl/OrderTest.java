package com.li.dao.impl;

import java.sql.Date;
import java.util.Calendar;

import com.li.dao.IOrderDao;
import com.li.exception.DataAccessException;
import com.li.pojo.Order;
import com.li.pojo.OrderLine;

public class OrderTest {

	public static void main(String[] args) {
		IOrderDao orderDao=new OrderDaoImpl();
		
		OrderLine ol1=new OrderLine();
		ol1.setProduct("java");
		ol1.setPrice(200.6);
		ol1.setQuantity(5L);
		
		OrderLine ol2=new OrderLine();
		ol2.setProduct("jdbc");
		ol2.setPrice(1560.6);
		ol2.setQuantity(10L);
		
		Order order=new Order();
		/*获取系统当前的时间*/
		order.setOrderedDate(new Date(System.currentTimeMillis()));
		Calendar cl=Calendar.getInstance();
		cl.set(Calendar.DAY_OF_MONTH, cl.get(Calendar.DAY_OF_MONTH)+3);
		/*三天后发货*/
		order.setshippedDate(new Date(cl.getTimeInMillis()));
		/*建立双向关联*/
		order.getOrderlines().add(ol1);
		order.getOrderlines().add(ol2);
		ol1.setOrder(order);
		ol2.setOrder(order);
		order.setTotal(ol1.getPrice()*ol1.getQuantity()+ol2.getPrice()*ol2.getQuantity());
		
		try {
			orderDao.saveOrder(order);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	

}
