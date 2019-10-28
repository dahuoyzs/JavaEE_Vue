package com.waterwarm.orderdetails;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.waterwarm.order.OrderServer;

public class GetOrderDetail extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String ordercode=request.getParameter("ordercode");
		OrderDetailsServer orderDetailsServer=new OrderDetailsServer();
		JSONArray ja=orderDetailsServer.getOrderDetailByOrderCode(ordercode);
		orderDetailsServer.close();
		
		response.getWriter().append(ja.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
