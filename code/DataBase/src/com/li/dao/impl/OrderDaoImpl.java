package com.li.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.li.dao.IOrderDao;
import com.li.exception.DataAccessException;
import com.li.jdbc.advanced.ConnectionFactory;
import com.li.jdbc.advanced.DBUtils;
import com.li.pojo.Order;
import com.li.pojo.OrderLine;

public class OrderDaoImpl implements IOrderDao {

	public OrderDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveOrder(Order order) throws DataAccessException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;

		try {
			conn = ConnectionFactory.getConnection();
			conn.setAutoCommit(false);

			/* �Ȳ常���ٲ��ӱ� */
			
			String selectSQL = "select nextval('order_seq')";
			pstmt=conn.prepareStatement(selectSQL);
			rs=pstmt.executeQuery();
			Long orderid=0L;
			if(rs.next()) {
				orderid=rs.getLong(1);
			}
			String insertSQL="insert into t_order (id, ordered_date, shipped_date, total) values(?,?,?,?)";
			pstmt=conn.prepareStatement(insertSQL);
			pstmt.setLong(1, orderid);
			pstmt.setDate(2, order.getOrderedDate());
			pstmt.setDate(3, order.getshippedDate());
			pstmt.setDouble(4, order.getTotal());
			int orderRows=pstmt.executeUpdate();
			
			insertSQL="insert into t_orderline (id, price, quantity, product, order_id) value(?,?,?,?,?)";
			pstmt=conn.prepareStatement(insertSQL);
			int count=0;
			for(OrderLine orderline : order.getOrderlines()) {
				selectSQL="select nextval('orderline_seq')";
				PreparedStatement selectPstmt=conn.prepareStatement(selectSQL);
				rs=selectPstmt.executeQuery();
				Long olid=0L;
				if(rs.next()) {
					olid=rs.getLong(1);
				}
				pstmt.setLong(1, olid);
				pstmt.setDouble(2, orderline.getPrice());
				pstmt.setLong(3, orderline.getQuantity());
				pstmt.setString(4, orderline.getProduct());
				pstmt.setLong(5, orderid);
				count+=pstmt.executeUpdate();
			}
			conn.commit();
			System.out.println("�ɹ����붩����¼��" + orderRows + "��");
			System.out.println("�ɹ����붩����ϸ��¼��" + count + "��");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DataAccessException("���涩������");
		} finally {
			DBUtils.close(conn);
			DBUtils.close(pstmt);
		}
	}

	@Override
	public void deleteOrder(Long id) throws DataAccessException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionFactory.getConnection();
			conn.setAutoCommit(false);

			/* ɾ����ϸ���� */
			String delSQL = "delete from t_orderline where order_id=?";
			pstmt = conn.prepareStatement(delSQL);
			pstmt.setLong(1, id);
			int olRows = pstmt.executeUpdate();

			/* ɾ�������� */
			delSQL = "delete from t_order where id=?";
			pstmt = conn.prepareStatement(delSQL);
			pstmt.setLong(1, id);
			int orderRows = pstmt.executeUpdate();

			conn.commit();
			System.out.println("�ɹ�ɾ����" + olRows + "��������ϸ��¼");
			System.out.println("�ɹ�ɾ����" + orderRows + "��������¼");

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DataAccessException("ɾ����������");
		} finally {
			DBUtils.close(conn);
			DBUtils.close(pstmt);
		}
	}

	@Override
	public void updateOrder(Order order) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public Order findOrder(Long id) throws DataAccessException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Order order = new Order();

		try {
			conn = ConnectionFactory.getConnection();
			conn.setAutoCommit(false);

			String selectSQL = "select id, ordered_date, shipped_date, total from t_order where id=?";
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				order.setId(rs.getLong(1));
				order.setOrderedDate(rs.getDate(2));
				order.setshippedDate(rs.getDate(3));
				order.setTotal(rs.getDouble(4));
			}

			/* �������ң�ͬʱ��������ϸ���ҳ��� */
			selectSQL="select id, price, quantity, product from t_orderline where order_id=?";
			pstmt=conn.prepareStatement(selectSQL);
			pstmt.setLong(1, id);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				OrderLine ol=new OrderLine();
				ol.setId(rs.getLong(1));
				ol.setPrice(rs.getDouble(2));
				ol.setQuantity(rs.getLong(3));
				ol.setProduct(rs.getString(4));
				/*����˫�������ϵ*/
				/*����������ӵ�������ϸ*/
				ol.setOrder(order);
				/*��������ϸ��ӵ�������*/
				order.getOrderlines().add(ol);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DataAccessException("���Ҷ�������");
		}finally {
			DBUtils.close(conn);
			DBUtils.close(pstmt);
			DBUtils.close(rs);
		}
		return order;
	}

	@Override
	public List<Order> findOrders() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
