package com.waterwarm.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CSfilter implements Filter
{
	public CSfilter()
	{
	}

	public void destroy()
	{
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT,DELETE");
//		System.out.println("过滤器执行了");
		request.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
		response.setContentType("text/html;charset=utf-8");
	}

	public void init(FilterConfig fConfig) throws ServletException
	{
	}
}
