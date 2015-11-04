
package com.danz.javacourse;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MTA_ProjectServlet extends HttpServlet {
	public void main(String[] args){
		String firstName, lastName;
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.print("Enter your name bitch ");
		firstName = scnr.next();
		System.out.print("Enter your last name bitch ");
		lastName = scnr.next();
		
		System.out.print("We start the java project with " + firstName + " " + lastName);
		
		
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world!!!!!!!!!!!!!!!!!!! :)))))");
	}
}
