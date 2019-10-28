package com.waterwarm.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import com.waterwarm.goods.GoodsServer;



public class ShowOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		int pagenow=1;
		int pagelimit=1; 
		pagenow=Integer.parseInt(request.getParameter("pagenow"));
		pagelimit=Integer.parseInt(request.getParameter("pagelimit"));
		
		OrderServer orderServer=new OrderServer();
		JSONArray ja=new JSONArray();
		ja=orderServer.showOrderByPageNum(pagenow, pagelimit);
		
		orderServer.close();
		System.out.println(ja.toString());
		response.getWriter().append(ja.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
