package com.li.dao;

import java.util.List;

import com.li.exception.DataAccessException;
import com.li.pojo.Order;

/*pojo���Dao�ӿ�*/

public interface IOrderDao {
	void saveOrder(Order order) throws DataAccessException;
	void deleteOrder(Long id) throws DataAccessException;
	void updateOrder(Order order) throws DataAccessException;
	Order findOrder(Long id) throws DataAccessException;
	List<Order> findOrders() throws DataAccessException;
}