package com.waterwarm.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLHandler
{
	public Connection conn = null;
	public Statement statement = null;

	public SQLHandler()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/waterwarm";
			String user = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, user, password);
			statement = conn.createStatement();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
