package com.waterwarm.orderdetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.waterwarm.sql.SQLHandler;

public class OrderDetailsServer
{
	private SQLHandler sql;
	private ResultSet rs;
	public OrderDetailsServer()
	{
		sql= new SQLHandler();
	}
	public boolean add(JSONObject jo)
	{
		try
		{
			String sqlstr="insert into orderdetails values("
			+jo.getInt("orderdetailsid")+",'"
			+jo.getString("ordercode")+"',"
			+jo.getInt("goodsid")+","
			+jo.getInt("number")+");";
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
	public boolean delete(int odid)
	{
		String sqlstr=" delete from orderdetails where orderdetailsid="
		+odid+";";
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

	public boolean update(int odid,JSONObject jo)
	{
		try
		{
			String sqlstr="update orderdetails set "
			+"orderdetailsid="+jo.getInt("orderdetailsid")
			+" ordercode='"+jo.getString("ordercode")
			+"' goodsid="+jo.getInt("goodsid")
			+" number="+jo.getInt("number")
			+ " where orderdetailsid="+odid;
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
	public JSONArray getPrintOrders(JSONArray ja)  {
		JSONArray jsonRes=new JSONArray();
		try
		{
			for (int i = 0; i < ja.length(); i++)
			{
				String ocode=ja.getString(i);
				String sqlstr="select * from `odview` where ordercode ='"+ocode+"'";
				JSONArray jArray=new JSONArray();
				System.out.println(sqlstr);
				rs=sql.statement.executeQuery(sqlstr);
				String confirmdate="";
				double countprice=0;
				String salename="";
				
				while(rs.next())
				{
					int number = rs.getInt(1);
					confirmdate = rs.getString(2);
					countprice = rs.getDouble(3);
					salename = rs.getString(4);
					//String ordercode = rs.getString(5);
					String goodsname = rs.getString(6);
					double price = rs.getDouble(7);
					
					jArray.put(new JSONObject()
						.put("number", number)
						//.put("confirmdate", confirmdate.substring(0, confirmdate.length()-2))
						//.put("countprice", countprice)
						//.put("salename", salename)
						//.put("ordercode", ordercode)
						.put("goodsname", goodsname)
						.put("price", price)
						);
				}
				jsonRes.put(new JSONObject()
					.put("ordercode", ocode)
					.put("confirmdate", confirmdate.substring(0, confirmdate.length()-2))
					.put("countprice", countprice)
					.put("salename", salename)
					.put("detail", jArray)
					);
			}
			System.out.println(jsonRes.toString());
			return jsonRes;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return jsonRes;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return jsonRes;
		}
	}
	public JSONArray getOrderDetailByOrderCode(String ocode) {
		String sqlstr="select * from `odview` where ordercode ='"+ocode+"'";
		
		JSONArray jArray=new JSONArray();
		try
		{
			
			System.out.println(sqlstr);
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int number = rs.getInt(1);
				String confirmdate = rs.getString(2);
				double countprice = rs.getDouble(3);
				String salename = rs.getString(4);
				String ordercode = rs.getString(5);
				String goodsname = rs.getString(6);
				double price = rs.getDouble(7);
				
				jArray.put(new JSONObject()
						.put("number", number)
						.put("confirmdate", confirmdate.substring(0, confirmdate.length()-2))
						.put("countprice", countprice)
						.put("salename", salename)
						.put("ordercode", ordercode)
						.put("goodsname", goodsname)
						.put("price", price)
						);
			}
			System.out.println(jArray.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
			return jArray;
		}
		return jArray;
	}
	public JSONObject select(int odid)
	{
		String sqlstr="select * from orderdetails where orderdetailsid="
		+odid+";";
		JSONObject jObject=new JSONObject();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int orderdetailsid=rs.getInt(1);
				String ordercode=rs.getString(2);
				int goodsid = rs.getInt(3);
				int number = rs.getInt(4);

				
				System.out.println("orderdetailsid："+orderdetailsid+" ordercode："+ordercode+" goodsid："+goodsid+"number："+number);
				jObject.put("orderdetailsid", orderdetailsid)
						.put("ordercode", ordercode)
						.put("goodsid", goodsid)
						.put("number", number);
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
		String sqlstr="select * from orderdetails";
		JSONArray ja=new JSONArray();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int orderdetailsid=rs.getInt(1);
				String ordercode=rs.getString(2);
				int goodsid = rs.getInt(3);
				int number = rs.getInt(4);
				System.out.println("orderdetailsid："+orderdetailsid+" ordercode："+ordercode+" goodsid："+goodsid+"number："+number);
				JSONObject jo=new JSONObject()
						.put("orderdetailsid", orderdetailsid)
						.put("ordercode", ordercode)
						.put("goodsid", goodsid)
						.put("number", number);
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
