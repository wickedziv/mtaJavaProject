package com.danz.javacourse;

public class Calculator{
	private double circleArea, triOpposite, uRadius, uHypotenuse, uDegree;
//	private double triOpposite;
//	private double radius;
//	private double 
	
	public void setRadius(double radius){
		uRadius = radius;
	}
	
	public double getRadius(){
		return uRadius;
	}
	
	public void setDegreeHypo(double degree, double hypotenuse){
		uHypotenuse = hypotenuse;
		uDegree = degree;
	}
	
	public double getHypotenuse(){
		return uHypotenuse;
	}
	
	public double getAngle(){
		return uDegree;
	}
	
	public double getCircleArea(){
		circleArea = Math.pow(uRadius, 2.0) * Math.PI;
		return circleArea;
	}
	
	public double getTriOpp(){
		triOpposite = Math.sin(Math.toRadians(uDegree)) * uHypotenuse;
		return triOpposite;
	}
	
	
}
