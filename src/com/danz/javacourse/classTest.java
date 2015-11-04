package com.danz.javacourse;

import java.util.Scanner;

public class classTest {
	public void main(String[] args){
		String firstName, lastName;
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.print("Enter your name bitch ");
		firstName = scnr.next();
		System.out.print("Enter your last name bitch ");
		lastName = scnr.next();
		
		System.out.print("We start the java project with " + firstName + " " + lastName);
		
		
	}
}
