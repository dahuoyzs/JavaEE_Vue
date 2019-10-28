package com.waterwarm.sale;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class SaleNameCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject joresult=new JSONObject();
		int b=-1;
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
			System.out.println(jo.toString());
			String salename=jo.getString("salename");
			if (salename.equals(""))
			{
				joresult.put("code", "404");
				joresult.put("msg", "账号不能为空");
			}else {
				SaleServer saleServer =new SaleServer();
				b=saleServer.checkSaleName(salename);
				saleServer.close();
				System.out.println(b);
				joresult.put("code", b);
				switch (b)
				{
				case 200:
					joresult.put("msg", "账号可用");
					break;
				case 400:
					joresult.put("msg", "账号已存在");
					break;
				case 500:
					joresult.put("msg", "数据库异常请联系管理员");
					break;
				}
			}
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
