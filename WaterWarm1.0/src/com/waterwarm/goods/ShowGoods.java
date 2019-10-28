package com.waterwarm.goods;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;



public class ShowGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int pagenow=1;
		int pagelimit=1; 
		String goodsclass="";
		pagenow=Integer.parseInt(request.getParameter("pagenow"));
		pagelimit=Integer.parseInt(request.getParameter("pagelimit"));
		goodsclass=request.getParameter("goodsclass");
		GoodsServer goodsServer=new GoodsServer();
		JSONArray ja=new JSONArray();
		
		if (goodsclass!=null&&!goodsclass.equals(""))
		{
			System.out.println("if"+goodsclass);
			ja=goodsServer.showGoodsByPageNum(pagenow, pagelimit,goodsclass);
		}else {
			System.out.println("else"+goodsclass);
			ja=goodsServer.showGoodsByPageNum(pagenow, pagelimit);
		}
		goodsServer.close();
		System.out.println(ja.toString());
		response.getWriter().append(ja.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
