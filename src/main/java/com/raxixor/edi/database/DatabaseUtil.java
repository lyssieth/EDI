package com.raxixor.edi.database;

import com.raxixor.edi.Constants;

import java.sql.*;

/**
 * Created by raxix on 18/03/2017, 15:50.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
class DatabaseUtil {
	
	/**
	 * Connect to the PostgreSQL Database
	 * 
	 * @return a Connection object
	 * @throws java.sql.SQLException
	 */
	static Connection connect() throws SQLException {
		return DriverManager.getConnection(Constants.DB_CONNECTION_URL, Constants.DB_CONNECTION_USR, Constants.DB_CONNECTION_PAS);
	}
}
