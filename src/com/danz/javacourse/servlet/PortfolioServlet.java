package com.danz.javacourse.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danz.javacourse.model.Portfolio;
import com.danz.javacourse.model.Stock;
import com.danz.javacourse.service.PortfolioManager;

@SuppressWarnings({"serial", "deprecation"})
public class PortfolioServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		PortfolioManager portfolioManager = new PortfolioManager();
		
		Portfolio portfolio1 = portfolioManager.getPortfolio();
		
		resp.getWriter().println(portfolio1.getHtmlString());
	}
}
