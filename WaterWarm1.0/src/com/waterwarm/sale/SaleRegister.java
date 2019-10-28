package com.waterwarm.sale;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class SaleRegister
 */
public class SaleRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SaleRegister() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject joresult=new JSONObject();
		int b=-1;
		boolean bb=false;
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
			}else {
				SaleServer saleServer =new SaleServer();
				b=saleServer.checkSaleName(salename);
				switch (b)
				{
				case 200:
					bb=saleServer.add(jo);
//					System.out.println("bb:"+bb);
					if (bb)
					{
						joresult.put("msg", "注册成功");
					}else {
						joresult.put("msg", "注册成功");
					}
					break;
				case 400:
					joresult.put("msg", "账号已存在");
					break;
				case 500:
					joresult.put("msg", "数据库异常请联系管理员");
					break;
				}
				saleServer.close();
				joresult.put("code", b);
				
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
			response.getWriter().append("请求方式不正确,必须为post,参数为json类型");
		}
//		System.out.println(joresult.toString()+"bb:"+bb);
		response.getWriter().append(joresult.toString());
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
