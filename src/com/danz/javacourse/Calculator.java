package com.danz.javacourse;

public class Calculator{
	private static double circleArea;
	private static double triOpposite;
	
	public void calculateCircle(int radius){
		circleArea = Math.pow(radius, 2.0) * Math.PI;
	}
	
	public double getCircleArea(){
		return circleArea;
	}
	
	public void calculateTriOpp(double degree, double hypotenuse){
		triOpposite = Math.sin(Math.toRadians(degree)) * hypotenuse;
	}
	
	public double getTriOpp(){
		return triOpposite;
	}
	
	public void main(String[] args){
		Calculator circle, triangle;
		circle = new Calculator();
		triangle = new Calculator();
		
		circle.calculateCircle(50);
		triangle.calculateTriOpp(30, 50);
				
	}
}