package com.danz.javacourse;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings({ "deprecation", "serial"})
public class StockDetailsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			
		resp.setContentType("text/html");
		Stock stock1 = new Stock("PIH", 13.1f, 12.4f, new Date("11/15/2015"));
		Stock stock2 = new Stock("AAL", 5.78f, 5.5f, new Date("11/15/2015"));
		Stock stock3 = new Stock("CAAS", 32.2f, 31.5f, new Date("11/14/2015"));

		resp.getWriter().print(
				stock1.getHtmlDescription() + "<br></br>" +  
				stock2.getHtmlDescription() + "<br></br>" +
				stock3.getHtmlDescription()
				);
	}
}
