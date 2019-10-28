package com.waterwarm.address;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.waterwarm.sql.SQLHandler;




public class AddressServer
{
	private SQLHandler sql;
	private ResultSet rs;
	public AddressServer()
	{
		sql= new SQLHandler();
	}

	public boolean add(JSONObject jo)
	{
		try
		{
			String sqlstr="insert into address values("
					+jo.getInt("addressid")+","
					+jo.getInt("userid")+",'"
					+jo.getString("receivename")+"','"
					+jo.getString("receivephone")+"',"
					+jo.getString("address")+");";
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
	public boolean delete(int addressid)
	{
		String sqlstr=" delete from address where addressid="
		+addressid+";";
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

	public boolean update(int oid,JSONObject jo)
	{
		try
		{
			String sqlstr="update address set "
			+"addressid="+jo.getInt("addressid")
			+" userid="+jo.getInt("userid")
			+" receivename='"+jo.getString("receivename")
			+"' receivephone='"+jo.getString("receivename")
			+"' address='"+jo.getString("address")
			+ "' where addressid="+oid;
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
	public JSONObject select(int aid)
	{
		String sqlstr="select * from address where addressid="
		+aid+";";
		JSONObject jObject=new JSONObject();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int addressid=rs.getInt(1);
				int userid=rs.getInt(2);
				String receivename= rs.getString(3);
				String receivephone = rs.getString(4);
				String address = rs.getString(5);
				
				System.out.println("addressid："+addressid+" userid："+userid+" receivename："+receivename+"receivephone："+receivephone+"address:"+address);
				jObject.put("addressid", addressid)
						.put("userid", userid)
						.put("receivename", receivename)
						.put("receivephone", receivephone)
						.put("address", address);

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
		String sqlstr="select * from address";
		JSONArray ja=new JSONArray();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int addressid=rs.getInt(1);
				int userid=rs.getInt(2);
				String receivename= rs.getString(3);
				String receivephone = rs.getString(4);
				String address = rs.getString(5);
				
				System.out.println("addressid："+addressid+" userid："+userid+" receivename："+receivename+"receivephone："+receivephone+"address:"+address);
				JSONObject jo=new JSONObject()
						.put("addressid", addressid)
						.put("userid", userid)
						.put("receivename", receivename)
						.put("receivephone", receivephone)
						.put("address", address);
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
