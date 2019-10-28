package com.waterwarm.sale;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.waterwarm.sql.SQLHandler;





public class SaleServer
{
	private SQLHandler sql;
	private ResultSet rs;
	public SaleServer()
	{
		sql= new SQLHandler();
	}
	public boolean add(JSONObject jo)
	{
		try
		{
			String sqlstr="insert into sale(salename,password)values('"
			+jo.getString("salename")+"','"
			+jo.getString("password")+"');";
			
			boolean b=sql.statement.execute(sqlstr);
			return b;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	public boolean delete(int sid)
	{
		String sqlstr=" delete from sale where saleid="
		+sid+";";
		try
		{
			boolean b=sql.statement.execute(sqlstr);
			return b;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(int sid,JSONObject jo)
	{
		try
		{
			String sqlstr="update sale set "
			+"saleid="+jo.getInt("saleid")
			+" saleaccount="+jo.getString("saleaccount")
			+" salename='"+jo.getString("salename")
			+" salepassword='"+jo.getString("salepassword")
			+ "' where saleid="+sid;
			boolean b=sql.statement.execute(sqlstr);
			return b;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	public JSONObject select(int sid)
	{
		String sqlstr="select * from sale where saleid="
		+sid+";";
		JSONObject jObject=new JSONObject();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int saleid=rs.getInt(1);
				String saleaccount=rs.getString(2);
				String salename = rs.getString(3);
				String salepassword = rs.getString(4);
				
				System.out.println("saleid："+saleid+" saleaccount："+saleaccount+" salename："+salename+"salepassword："+salepassword);
				jObject.put("saleid", saleid)
						.put("saleaccount", saleaccount)
						.put("salename", salename)
						.put("salepassword", salepassword);
			}
			return jObject;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public JSONArray selectALL()
	{
		String sqlstr="select * from sale";
		JSONArray ja=new JSONArray();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int saleid=rs.getInt(1);
				String saleaccount=rs.getString(2);
				String salename = rs.getString(3);
				String salepassword = rs.getString(4);
				System.out.println("saleid："+saleid+" saleaccount："+saleaccount+" salename："+salename+"salepassword："+salepassword);
				JSONObject jo=new JSONObject()
						.put("saleid", saleid)
						.put("saleaccount", saleaccount)
						.put("salename", salename)
						.put("salepassword", salepassword);
				ja.put(jo);
			}
			return ja;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public boolean Verification(String salename,String password)
	{
		String sqlstr="select * from sale where salename='"+salename+"';";
		String ps="";
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while (rs.next())
			{
				ps=rs.getString(3);
			}
			return password.equals(ps)?true:false;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return  false;
		} 
	}
	public int checkSaleName(String sn)
	{
		String sqlstr="select salename from sale where salename='"+sn+"';";
		System.out.println(sqlstr);
		String saleaccount="";
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while (rs.next())
			{
				saleaccount=rs.getString(1);
			}
			return saleaccount.equals(sn)?400:200;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return 500;
		}
	}
	public void close()
	{
		
		try
		{
			if (rs!=null)
			{
				rs.close();
			}
			sql.statement.close();
			sql.conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
