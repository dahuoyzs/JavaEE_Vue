package com.waterwarm.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.waterwarm.sql.SQLHandler;

public class MappingServer
{
	private SQLHandler sql;
	private ResultSet rs;
	public MappingServer()
	{
		sql= new SQLHandler();
	}
	public boolean add(JSONObject jo)
	{
		try
		{
			String sqlstr="insert into mapping values("
			+jo.getInt("mappingid")+","
			+jo.getInt("groupid")+","
			+jo.getInt("goodsid")+");";
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
	public boolean delete(int mid)
	{
		String sqlstr=" delete from mapping where mappingid="
		+mid+";";
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

	public boolean update(int mid,JSONObject jo)
	{
		try
		{
			String sqlstr="update mapping set "
			+"mappingid="+jo.getInt("mappingid")
			+" groupid="+jo.getInt("groupid")
			+" goodsid="+jo.getInt("goodsid")
			+ " where mappingid="+mid;
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
	public JSONObject select(int mid)
	{
		String sqlstr="select * from mapping where mappingid="
		+mid+";";
		JSONObject jObject=new JSONObject();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int mappingid=rs.getInt(1);
				int groupid=rs.getInt(2);
				int goodsid=rs.getInt(3);
				System.out.println("mappingid："+mappingid+" groupid："+groupid+" goodsid："+goodsid);
				jObject.put("mappingid", mappingid)
						.put("groupid", groupid)
						.put("goodsid", goodsid);
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
		String sqlstr="select * from mapping";
		JSONArray ja=new JSONArray();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int mappingid=rs.getInt(1);
				int groupid=rs.getInt(2);
				int goodsid=rs.getInt(3);

				System.out.println("mappingid："+mappingid+" groupid："+groupid+" goodsid："+goodsid);
				JSONObject jo=new JSONObject()
						.put("mappingid", mappingid)
						.put("groupid", groupid)
						.put("goodsid", goodsid);
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
