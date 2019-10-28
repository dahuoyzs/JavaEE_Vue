package com.waterwarm.goods;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class BootstrapTable extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("请求成功结果已经返回");
		JSONObject jo = new JSONObject();
		try
		{
			JSONArray ja=new JSONArray();
			for (int i = 0; i < 30; i++)
			{
				ja.put(new JSONObject().put("name", "张杰").put("age", i+10).put("sex", "man"));
			}
			
			jo.put("total", 22);
			jo.put("rows", ja);

		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		response.getWriter().append(jo.toString());
		
//		{total:22,row:{name:\"jacksaf\",age:18,sex:\"man\\\"}}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
