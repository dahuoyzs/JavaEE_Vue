package com.waterwarm.sale;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class SaleLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject joresult=new JSONObject();
		boolean b=false;
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
			String password=jo.getString("password");
			if (salename.equals("")||password.equals(""))
			{
				joresult.put("code", "404")
				.put("msg", "表单不能为空");
				response.getWriter().append(joresult.toString());
			}else {
				SaleServer saleServer =new SaleServer();
				b=saleServer.Verification(salename, password);
				saleServer.close();
				if (b)
				{
					joresult.put("code", "200")
					.put("msg", "账号和密码匹配");
				}else {
					joresult.put("code", "303")
					.put("msg", "账号和密码不匹配");
				}
				response.getWriter().append(joresult.toString());
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
			response.getWriter().append("请求方式不正确,必须为post,参数为json类型");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
