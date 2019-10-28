package com.waterwarm.goods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.ws.db.glassfish.BridgeWrapper;
import com.waterwarm.sql.SQLHandler;

public class GoodsServer
{
	private SQLHandler sql;
	private ResultSet rs;
	public GoodsServer()
	{
		sql= new SQLHandler();
	}
	public boolean add(JSONObject jo)
	{
		try
		{
			String sqlstr="insert into goods values("
			+jo.getInt("goodsid")+",'"
			+jo.getString("goodsclass")+"','"
			+jo.getString("goodsname")+"',"
			+jo.getDouble("price")+",'"
			+jo.getString("unit")+"','"
			+jo.getString("brand")+"');";
			
			System.out.println(sqlstr);
			boolean b=sql.statement.execute(sqlstr);
			return b;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return true;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return true;
		}
	}
	public boolean delete(int gid)
	{
		String sqlstr=" delete from goods where goodsid="
		+gid+";";
		try
		{
			boolean b=sql.statement.execute(sqlstr);
			return b;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return true;
		}
	}

	public boolean update(int gid,JSONObject jo)
	{
		try
		{
			String sqlstr="update goods set "
			+"goodsid="+jo.getInt("goodsid")
			+", goodsclassname='"+jo.getString("goodsclass")
			+"', goodsname='"+jo.getString("goodsname")
			+"', price="+jo.getDouble("price")
			+", unit='"+jo.getString("unit")
			+"', brand='"+jo.getString("brand")
			+ "' where goodsid="+gid;
			System.out.println(sqlstr);
			boolean b=sql.statement.execute(sqlstr);
			return b;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return true;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return true;
		}
	}
	public JSONObject select(int gid)
	{
		String sqlstr="select * from goods where goodsid="
		+gid+";";
		JSONObject jObject=new JSONObject();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int goodsid=rs.getInt(1);
				int goodsclassid=rs.getInt(2);
				String goodsname = rs.getString(3);
				double price = rs.getDouble(4);
				String unit = rs.getString(5);
				String brand = rs.getString(6);
				
				//System.out.println("goodsid："+goodsid+" goodsclassid："+goodsclassid+" goodsname："+goodsname+"price："+price+"unit:"+unit+"brand:"+brand);
				jObject.put("goodsid", goodsid)
						.put("goodsclassid", goodsclassid)
						.put("goodsname", goodsname)
						.put("price", price)
						.put("unit", unit)
						.put("brand", brand);
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
		String sqlstr="select * from goods";
		JSONArray ja=new JSONArray();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int goodsid=rs.getInt(1);
				String goodsclassid=rs.getString(2);
				String goodsname = rs.getString(3);
				double price = rs.getDouble(4);
				String unit = rs.getString(5);
				String brand = rs.getString(6);
				//System.out.println("goodsid："+goodsid+" goodsclassid："+goodsclassid+" goodsname："+goodsname+"price："+price+"unit:"+unit+"brand:"+brand);
				JSONObject jo=new JSONObject()
						.put("goodsid", goodsid)
						.put("goodsclassid", goodsclassid)
						.put("goodsname", goodsname)
						.put("price", price)
						.put("unit", unit)
						.put("brand", brand);
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
	public JSONObject selectMaxgoodsID()
	{
		String sqlstr="select max(goodsid) from goods";
		JSONObject jo=new JSONObject();
		try
		{
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				int maxid=rs.getInt(1);
				jo.put("maxid", maxid).put("code","200");
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
	public JSONObject selectALLByClass()
	{
		String sqlstr1="select * from goodsclass";
		JSONObject jo=new JSONObject();
		
		ArrayList<Integer> gcids=new ArrayList<Integer>();
		ArrayList<String> gcnames=new ArrayList<String>();
		try
		{
			rs=sql.statement.executeQuery(sqlstr1);
			while(rs.next())
			{
				int gcid=rs.getInt(1);
				String goodsclassname = rs.getString(2);
				gcids.add(gcid);
				gcnames.add(goodsclassname);
			}
			for (int i = 0; i < gcids.size(); i++)
			{
				int id=gcids.get(i);
				String name=gcnames.get(i);
				String sqlstr2=" select * from goods where goodsclassid="+id+";";
				ResultSet resultSet=sql.statement.executeQuery(sqlstr2);
				JSONArray ja=new JSONArray();
				while(resultSet.next())
				{
					int goodsid=resultSet.getInt(1);
					int goodsclassid=resultSet.getInt(2);
					String goodsname = resultSet.getString(3);
					int price = resultSet.getInt(4);
					String unit = resultSet.getString(5);
					String brand = resultSet.getString(6);
					//System.out.println("goodsid："+goodsid+" goodsclassid："+goodsclassid+" goodsname："+goodsname+"price："+price+"unit:"+unit+"brand:"+brand);
					JSONObject jo1=new JSONObject()
							.put("goodsid", goodsid)
							.put("goodsclassid", goodsclassid)
							.put("goodsname", goodsname)
							.put("price", price)
							.put("unit", unit)
							.put("brand", brand);
					ja.put(jo1);
				}
				jo.put(name, ja);
			}
			return jo;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return new JSONObject();
		} catch (JSONException e)
		{
			e.printStackTrace();
			return new JSONObject();
		}
	}
	public JSONObject selectALLByBrand()
	{
		String sqlstr1="select brand from goods group by brand";
		
		JSONObject jo=new JSONObject();
		ArrayList<String> brands=new ArrayList<String>();
		try
		{
			rs=sql.statement.executeQuery(sqlstr1);
			while(rs.next())
			{
				String brand = rs.getString(1);
				brands.add(brand);
			}
			for (int i = 0; i < brands.size(); i++)
			{
				String b=brands.get(i);
				String sqlstr2=" select * from goods where brand='"+b+"';";
				ResultSet resultSet=sql.statement.executeQuery(sqlstr2);
				JSONArray ja=new JSONArray();
				while(resultSet.next())
				{
					int goodsid=resultSet.getInt(1);
					int goodsclassid=resultSet.getInt(2);
					String goodsname = resultSet.getString(3);
					int price = resultSet.getInt(4);
					String unit = resultSet.getString(5);
					String brand = resultSet.getString(6);
					//System.out.println("goodsid："+goodsid+" goodsclassid："+goodsclassid+" goodsname："+goodsname+"price："+price+"unit:"+unit+"brand:"+brand);
					JSONObject jo1=new JSONObject()
							.put("goodsid", goodsid)
							.put("goodsclassid", goodsclassid)
							.put("goodsname", goodsname)
							.put("price", price)
							.put("unit", unit)
							.put("brand", brand);
					ja.put(jo1);
				}
				jo.put(b, ja);
			}
			return jo;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return new JSONObject();
		} catch (JSONException e)
		{
			e.printStackTrace();
			return new JSONObject();
		}
	}
	public JSONObject getGoodsClass()
	{
		JSONObject jo=new JSONObject();
		String sqlstr1="select goodsclassname from goods group by goodsclassname";
		ArrayList<String> goodsclassnames=new ArrayList<String>();
		try
		{
			rs=sql.statement.executeQuery(sqlstr1);
			while(rs.next())
			{
				String goodsclassname = rs.getString(1);
				goodsclassnames.add(goodsclassname);
			}
			for (String key : goodsclassnames)
			{
				int num=0;
				String sqlstr2=" select count(*) from goods where goodsclassname='"+key+"'";
				rs=sql.statement.executeQuery(sqlstr2);
				while(rs.next())
				{
					num = rs.getInt(1);
				}
				jo.put(key, num);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return jo;
	}
	public JSONArray showGoodsByPageNum(int pagenow,int pagelimit)
	{
		String sqlstr="";
		if (pagenow<=1)
		{
			sqlstr="select  * from goods limit "+pagelimit+";";
		}else {
			sqlstr="select  * from goods where goodsid not in(select goodsid from (select * from goods limit "+pagelimit*(pagenow-1)+") as tem) limit "+pagelimit+";";		}
		JSONArray jArray=new JSONArray();
		try
		{
			System.out.println(pagenow+"           "+pagelimit);
			System.out.println(sqlstr);
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				String goodsid = rs.getString(1);
				String goodsclassname = rs.getString(2);
				String goodsname = rs.getString(3);
				int price = rs.getInt(4);
				String unit = rs.getString(5);
				String brand = rs.getString(6);
				jArray.put(new JSONObject()
						.put("goodsid", goodsid)
						.put("goodsclassname", goodsclassname)
						.put("goodsname", goodsname)
						.put("price", price)
						.put("unit", unit)
						.put("brand", brand)
						);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return jArray;
	}
	public JSONArray showGoodsByPageNum(int pagenow,int pagelimit,String gc)
	{
		String sqlstr="";
		if (pagenow<=1)
		{
			sqlstr="select  * from goods where goodsclassname = '"+gc+"' limit "+pagelimit+";";
		}else {
			//sqlstr="select  * from goods where goodsid not in(select goodsid from (select * from goods limit "+pagelimit*(pagenow-1)+") as tem) limit "+pagelimit+";";		}

			sqlstr="select  * from goods where goodsid not in(select goodsid from (select * from goods limit "+pagelimit*(pagenow-1)+") as tem) and goodsclassname = '"+gc+"'  limit "+pagelimit+";";		}
		JSONArray jArray=new JSONArray();
		try
		{
			System.out.println(pagenow+"           "+pagelimit);
			System.out.println(sqlstr);
			rs=sql.statement.executeQuery(sqlstr);
			while(rs.next())
			{
				String goodsid = rs.getString(1);
				String goodsclassname = rs.getString(2);
				String goodsname = rs.getString(3);
				int price = rs.getInt(4);
				String unit = rs.getString(5);
				String brand = rs.getString(6);
				jArray.put(new JSONObject()
						.put("goodsid", goodsid)
						.put("goodsclassname", goodsclassname)
						.put("goodsname", goodsname)
						.put("price", price)
						.put("unit", unit)
						.put("brand", brand)
						);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return jArray;
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
