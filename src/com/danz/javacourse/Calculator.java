package com.danz.javacourse;

public class Calculator{
	private double circleArea;
	private double triOpposite;
	
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
	
}
