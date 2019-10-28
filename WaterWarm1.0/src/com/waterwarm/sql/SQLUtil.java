package com.waterwarm.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUtil
{
	public static Connection conn = null;
	public static PreparedStatement ps = null;
	public static PreparedStatement ps2 = null;
	public static PreparedStatement ps3 = null;
	public static ResultSet rs = null;

	public static void getUtil()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/waterwarm", "root", "123456");
			// 预加载sql语句
			ps = conn.prepareStatement(" student set age=30 where name='rose'");
			ps2 = conn.prepareStatement("update student set age=35 where name='jock'");
			ps3 = conn.prepareStatement("select * from student");
			// 设置自动提交事务为false；//手动提交
			conn.setAutoCommit(false);
			// 提交预加载sql语句
			ps.executeUpdate();
			ps2.executeUpdate();
			// conn.rollback();
			// 提交事务
			conn.commit();
			rs = ps3.executeQuery();
			while (rs.next())
			{
				int age = rs.getInt("age");
				String name = rs.getString("name");
				System.out.println("age ：" + age + " name " + name);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				ps.close();
				ps2.close();
				ps3.close();
				conn.close();
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}
}
