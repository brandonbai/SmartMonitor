package org.jifeihu.smartshed.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库工具类
 */
public class DBUtils {
	private static ComboPooledDataSource pool = new ComboPooledDataSource();

	private DBUtils() {
	}

	/**
	 * 获取数据库连接对象对象
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConn() throws SQLException {
		return  pool.getConnection();
	}
	
	/**
	 * 实现增删改
	 * @param sql
	 * @param params
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public static int update(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			if(params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1, params[i]);
				}
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, null);
		}
	}
	/**
	 * 工具方法: 释放资源
	 * 
	 * @param conn
	 * @param stat
	 * @param rs
	 */
	public static void close(Connection conn, Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				stat = null;
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
}
