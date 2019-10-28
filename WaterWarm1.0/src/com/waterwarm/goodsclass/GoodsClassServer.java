package com.waterwarm.goodsclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.waterwarm.sql.SQLHandler;

public class GoodsClassServer
{
	private SQLHandler sql;
	private ResultSet rs;
	public GoodsClassServer()
	{
		sql= new SQLHandler();
	}
	public boolean add(JSONObject jo)
	{
		try
		{
			String sqlstr="insert into goodsclass values("
			+jo.getInt("goodsclassid")+",'"
			+jo.getString("goodsclassname")+"');";
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
	public boolean delete(int gcid)
	{
		String sqlstr=" delete from goodsclass where goodsclassid="
		+gcid+";";
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

	public boolean update(int gcid,JSONObject jo)
	{
		try
		{
			String sqlstr="update goodsclass set "
			+"goodsclassid="+jo.getInt("goodsclassid")
			+" goodsclassname='"+jo.getString("goodsclassname")
			+ "' where goodsclassid="+gcid;
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
	public JSONObject select(int gcid)
	{
		String sqlstr="select * from goodsclass where goodsclassid="
		+gcid+";";
		JSONObject jObject=new JSONObject();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int goodsclassid=rs.getInt(1);
				String goodsclassname = rs.getString(2);
				System.out.println("goodsclassid："+goodsclassid+" goodsclassname："+goodsclassname);
				jObject.put("goodsclassid", goodsclassid)
						.put("goodsclassname", goodsclassname);
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
		String sqlstr="select * from goodsclass";
		JSONArray ja=new JSONArray();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int goodsclassid=rs.getInt(1);
				String goodsclassname=rs.getString(2);

				System.out.println("goodsclassid："+goodsclassid+" goodsclassname："+goodsclassname);
				JSONObject jo=new JSONObject()
						.put("goodsclassid", goodsclassid)
						.put("goodsclassname", goodsclassname);
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
