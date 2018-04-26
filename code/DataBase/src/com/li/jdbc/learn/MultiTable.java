package com.li.jdbc.learn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*多表查询的例子*/

public class MultiTable {

	public static void main(String[] args) {
		Connection c = DBUtil.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = c.createStatement();
			String sql = "select room_id, dic_name from room, tb_dic where dic_type='ROOM_TYPE' and room.room_type=tb_dic.dic_code;";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				System.out.print(rs.getString(1)+" ");
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(c);
		DBUtil.close(stat);
		DBUtil.close(rs);
	}

}
