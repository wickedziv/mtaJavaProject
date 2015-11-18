package com.danz.javacourse;

public class Calculator{
	private double circleArea, triOpposite, radius, hypotenuse, degree;
	
	public Calculator(double radius, double hypotenuse, double degree){
		setRadius(radius);
		setDegreeHypo(degree,hypotenuse);
	}
	
	public void setRadius(double radius){
		this.radius = radius;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setDegreeHypo(double degree, double hypotenuse){
		this.hypotenuse = hypotenuse;
		this.degree = degree;
	}
	
	public double getHypotenuse(){
		return hypotenuse;
	}
	
	public double getAngle(){
		return degree;
	}
	
	public double getCircleArea(){
		circleArea = Math.pow(radius, 2.0) * Math.PI;
		return circleArea;
	}
	
	public double getTriOpp(){
		triOpposite = Math.sin(Math.toRadians(degree)) * hypotenuse;
		return triOpposite;
	}
}
