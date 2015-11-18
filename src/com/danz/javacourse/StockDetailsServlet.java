package com.danz.javacourse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings({ "deprecation", "serial"})
public class StockDetailsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		Stock stock1 = new Stock("PIH", 13.1f, 12.4f, new Date("11/15/2015"));
		Stock stock2 = new Stock("AAL", 5.78f, 5.5f, new Date("11/15/2015"));
		Stock stock3 = new Stock("CAAS", 32.2f, 31.5f, new Date("11/14/2015"));

		resp.getWriter().println(
				"Stock symbol: " + stock1.getSymbol() + " ask: " + stock1.getAsk() + " bid: " + stock1.getBid() + " " + sdf.format(stock1.getDate())
				+ "\n" + "Stock symbol: " + stock2.getSymbol() + " ask: " + stock2.getAsk() + " bid: " + stock2.getBid() + " " + sdf.format(stock2.getDate())
				+ "\n" + "Stock symbol: " + stock3.getSymbol() + " ask: " + stock3.getAsk() + " bid: " + stock3.getBid() + " " + sdf.format(stock3.getDate())
				);
	}
}
