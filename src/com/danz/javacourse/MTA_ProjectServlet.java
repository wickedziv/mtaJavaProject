
package com.danz.javacourse;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class MTA_ProjectServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Application successfully loaded");
		
		Calculator circle = new Calculator();
		Calculator triangle = new Calculator();
		
		DecimalFormat df = new DecimalFormat("0.000");
		
		circle.calculateCircle(50);
		triangle.calculateTriOpp(30, 50);
		resp.getWriter().println("Circle Area is " + df.format(circle.getCircleArea()) +
								 "\n" +
								 "Triangle opposite is " + df.format(triangle.getTriOpp()));
	}
}


