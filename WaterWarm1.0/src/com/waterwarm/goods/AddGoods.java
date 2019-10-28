package com.waterwarm.goods;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class AddGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject joresult=new JSONObject();
		try
		{
			StringBuffer buffer=new StringBuffer();
			BufferedReader reader=request.getReader();
			
			String line=null;
			while ((line=reader.readLine())!=null)
			{
				buffer.append(line);
			}
			JSONObject jo=new JSONObject(buffer.toString());
			

			
			GoodsServer goodsServer =new GoodsServer();
			boolean b=goodsServer.add(jo);
			goodsServer.close();
			
			
			joresult.put("code", b?"400":"200")
			.put("msg", b?"添加失败":"添加成功");
		} catch (JSONException e)
		{
			e.printStackTrace();
			response.getWriter().append("请求方式不正确,必须为post,参数为json类型");
		}
		response.getWriter().append(joresult.toString());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
