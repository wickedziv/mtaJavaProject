package com.danz.javacourse;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Exercise03 extends HttpServlet {
	public void doGet (HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		Calculator circle = new Calculator();
		Calculator triangle = new Calculator();
		
		DecimalFormat df = new DecimalFormat("0.000");
		
		circle.setRadius(50);
		triangle.setDegreeHypo(30, 50);
		
		resp.getWriter().println("Circle Area with radius " + circle.getRadius() + " is " + df.format(circle.getCircleArea()) +
								 "\n" +
								 "Triangle opposite where angle B is " + triangle.getAngle() + " and Hypotenuse length is " + 
								 triangle.getHypotenuse() + " is: " + df.format(triangle.getTriOpp()));
	}
}
