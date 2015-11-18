package com.danz.javacourse;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class Exercise03 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Calculator circleTriangleCalc = new Calculator(50,50,30);

		DecimalFormat df = new DecimalFormat("0.000");

		resp.getWriter().println(
				"Circle Area with radius " + circleTriangleCalc.getRadius() + " is "
						+ df.format(circleTriangleCalc.getCircleArea()) + "\n"
						+ "Triangle opposite where angle B is "
						+ circleTriangleCalc.getAngle() + " and Hypotenuse length is "
						+ circleTriangleCalc.getHypotenuse() + " is: "
						+ df.format(circleTriangleCalc.getTriOpp()));
	}
}
