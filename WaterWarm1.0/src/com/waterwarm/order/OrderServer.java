package com.waterwarm.order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.waterwarm.sql.SQLHandler;

public class OrderServer
{
	private SQLHandler sql;
	private ResultSet rs;
	
	public static Connection conn = null;
	public static PreparedStatement ps = null;
	public static PreparedStatement ps2 = null;
	public static PreparedStatement ps3 = null;
	public static ResultSet rs1 = null;

	public OrderServer()
	{
		sql= new SQLHandler();
	}
	public boolean add(JSONObject jo)
	{
		System.out.println(jo);
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/waterwarm", "root", "123456");
			// 预加载sql语句
			conn.setAutoCommit(false);
			String sqlstr="insert into `order` (ordercode,confirmdate,countprice,salename)"
					+ "values('"
					+jo.getString("ordercode")+"','"
					+jo.getString("confirmdate")+"',"
					+jo.getDouble("countprice")+",'"
					+jo.getString("salename")+"');";
			System.out.println(sqlstr);
			ps = conn.prepareStatement(sqlstr);
			ps.executeUpdate();
			JSONArray ja=jo.getJSONArray("orderdetails");
			for(int i=0;i<ja.length();i++)
			{
				JSONObject goodsJo=ja.getJSONObject(i);
				String sqlstr2="insert into orderdetails (ordercode,goodsid,number)values('"
						+jo.getString("ordercode")+"',"
						+goodsJo.getInt("goodsid")+","
						+goodsJo.getInt("number")+");";
				System.out.println(sqlstr2);
				ps2 = conn.prepareStatement(sqlstr2);
				ps2.executeUpdate();
			}
			conn.commit();
			return true;
		} catch (Exception e)
		{
			System.out.println("走catch");
			try
			{
				System.out.println("走catch_rollback");
				conn.rollback();
			} catch (SQLException e1)
			{
				System.out.println("走catch_catch");
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally
		{
			
			try
			{
				ps.close();
				ps2.close();
				conn.close();
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}
	public boolean delete(int oid)
	{
		String sqlstr=" delete from order where orderid="
		+oid+";";
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

			
			String sqlstr="update order set "
			+"orderid="+jo.getInt("orderid")
			+" ordercode='"+jo.getString("ordercode")
			+"' confirmdate='"+jo.getString("confirmdate")
			+"' countprice="+jo.getDouble("countprice")
			+" saleid="+jo.getInt("saleid")
			+" addressid="+jo.getInt("addressid")
			+" orderway='"+jo.getString("orderway")
			+"' orderstate='"+jo.getString("orderstate")
			+ "' where orderid="+oid;
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
	public JSONObject select(int oid)
	{
		String sqlstr="select * from order where orderid="
		+oid+";";
		JSONObject jObject=new JSONObject();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
//				String sqlstr="insert into order values("
//				+jo.getInt("orderid")+",'"
//				+jo.getString("ordercode")+"','"
//				+jo.getString("confirmdate")+"',"
//				+jo.getDouble("countprice")+","
//				+jo.getInt("saleid")+","
//				+jo.getInt("addressid")+",'"
//				+jo.getString("orderway")+"','"
//				+jo.getString("orderstate")+"');";
				int orderid=rs.getInt(1);
				String ordercode=rs.getString(2);
				String confirmdate = rs.getString(3);
				double countprice = rs.getDouble(4);
				int saleid=rs.getInt(5);
				int addressid=rs.getInt(6);
				String orderway = rs.getString(7);
				String orderstate = rs.getString(8);
				
				System.out.println("orderid："+orderid
						+" ordercode："+ordercode
						+" confirmdate："+confirmdate
						+" countprice："+countprice
						+" saleid:"+saleid
						+" addressid:"+addressid
						+" orderway:"+orderway
						+" orderstate:"+orderstate);
				jObject.put("orderid", orderid)
						.put("ordercode", ordercode)
						.put("confirmdate", confirmdate)
						.put("countprice", countprice)
						.put("saleid", saleid)
						.put("addressid", addressid)
						.put("orderway", orderway)
						.put("orderstate", orderstate);
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
	public JSONArray showOrderByPageNum(int pagenow,int pagelimit) {
		String sqlstr="";
		if (pagenow<=1)
		{
			sqlstr="select  * from `order` order by confirmdate desc limit "+pagelimit+" ;";
		}else {
//			sqlstr="select  * from `order` where orderid not in(select orderid from (select * from `order` order by confirmdate desc limit "+pagelimit*(pagenow-1)+","+pagelimit+")  as tem) limit "+pagelimit+";";		}
		sqlstr ="select * from `order` order by confirmdate desc limit "+pagelimit*(pagenow-1)+","+pagelimit+";";
		}
		JSONArray jArray=new JSONArray();
		try
		{
			System.out.println(pagenow+"           "+pagelimit);
			System.out.println(sqlstr);
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				String orderid = rs.getString(1);
				String ordercode = rs.getString(2);
				String confirmdate = rs.getString(3);
				double countprice = rs.getDouble(4);
				String salename = rs.getString(5);
				
				jArray.put(new JSONObject()
						.put("orderid", orderid)
						.put("ordercode", ordercode)
						.put("confirmdate", confirmdate)
						.put("countprice", countprice)
						.put("salename", salename)
						);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return jArray;
	}
	public JSONObject getOrderSize() {
		String sqlstr="SELECT count(*) FROM `order`";
		JSONObject jo=new JSONObject();
		try
		{
			
			rs=sql.statement.executeQuery(sqlstr);
			String ordersize="";
			while(rs.next())
			{
				ordersize=rs.getString(1);
			}
			
			if (ordersize==null||ordersize=="")
			{
				jo.put("code", "400");
			}else {
				jo.put("ordersize", ordersize).put("code", "200");
			}
			System.out.println(jo.toString());
			return jo;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return jo;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return jo;
		}
	}
	public JSONObject getOrderCodeByTodaydate(String tdate) {
		String sqlstr="select max(ordercode) from `order` where confirmdate >'"+tdate+"'";
		JSONObject jo=new JSONObject();
		try
		{
			System.out.println(sqlstr);
			rs=sql.statement.executeQuery(sqlstr);
			String maxOrderCode="";
			while(rs.next())
			{
				maxOrderCode=rs.getString(1);
			}
			
			if (maxOrderCode==null||maxOrderCode=="")
			{
				jo.put("code", "400");
			}else {
				jo.put("maxordercode", maxOrderCode).put("code", "200");
			}
			
			return jo;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return jo;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return jo;
		}
	}
	public JSONArray selectALL()
	{
		String sqlstr="select * from order";
		JSONArray ja=new JSONArray();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int orderid=rs.getInt(1);
				String ordercode=rs.getString(2);
				String confirmdate = rs.getString(3);
				double countprice = rs.getDouble(4);
				int saleid=rs.getInt(5);
				int addressid=rs.getInt(6);
				String orderway = rs.getString(7);
				String orderstate = rs.getString(8);
				System.out.println("orderid："+orderid
						+" ordercode："+ordercode
						+" confirmdate："+confirmdate
						+" countprice："+countprice
						+" saleid:"+saleid
						+" addressid:"+addressid
						+" orderway:"+orderway
						+" orderstate:"+orderstate);
				JSONObject jo=new JSONObject()
						.put("orderid", orderid)
						.put("ordercode", ordercode)
						.put("confirmdate", confirmdate)
						.put("countprice", countprice)
						.put("saleid", saleid)
						.put("addressid", addressid)
						.put("orderway", orderway)
						.put("orderstate", orderstate);
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
