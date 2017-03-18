package com.raxixor.edi.database;

import com.raxixor.edi.database.entities.user.UserInfo;

import java.sql.*;

/**
 * Created by raxix on 18/03/2017, 18:33.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class UserDatabase {
	
	public static int getUsercount() throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "SELECT count(*) FROM users";
		int count = 0;
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		conn.close();
		return rs.getInt(1);
	}
	
	public static UserInfo getUserInfo(String id) throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "SELECT * FROM users WHERE id = '" + id + "'";
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		conn.close();
		return new UserInfo(rs.getString("id"), rs.getBoolean("eval_enabled"));
	}
	
	public static void addUser(UserInfo info) throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "INSERT INTO users VALUES ('" +
				info.getId() + "', " + info.getEval() + ")";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		conn.close();
	}
	
	public static void removeUser(String id) throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "DELETE FROM users WHERE id = '" + id + "'";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		conn.close();
	}
	
	public static boolean userExists(String id) throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "SELECT id FROM users WHERE id = '" + id + "'";
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		conn.close();
		
		if (rs.next()) return true;
		return false;
	}
}
