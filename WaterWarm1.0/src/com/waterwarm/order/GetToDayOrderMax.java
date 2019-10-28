package com.waterwarm.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetToDayOrderMax extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String Todaydate=request.getParameter("date");
		OrderServer orderServer=new OrderServer();
		JSONObject jo=orderServer.getOrderCodeByTodaydate(Todaydate);
		orderServer.close();
		
		response.getWriter().append(jo.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
