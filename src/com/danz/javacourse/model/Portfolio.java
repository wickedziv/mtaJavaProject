package com.danz.javacourse.model;

import com.danz.javacourse.Stock;

public class Portfolio {
	private static final String title = new String("<h1>Dan & Ziv's Portfolios:</h1>");
	private static final String ERROR_MAXPORT_SIZE = ("Max Portoflio Size Exceeded!");
	private static final String HTMLBREAK = ("<br></br>");
	private static final int MAX_PORTFOLIO_SIZE = 5;
	private Stock[] stocks;
	private int portfolioSize = 0;
	
	public Portfolio(){
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
	}
	
	public synchronized Stock[] getStocks() {
		return stocks;
	}

	public synchronized void addStocks(Stock stock) {
		if(portfolioSize < MAX_PORTFOLIO_SIZE){
			stocks[portfolioSize] = stock;
			portfolioSize++;
		}
		else
			this.returnError();
	}
	
	public String returnError(){
		return ERROR_MAXPORT_SIZE; 
	}
	
	public String getHtmlString(){
		String s2 = "";
		for(int i=0; i < portfolioSize; i++){
			s2 += (stocks[i].getHtmlDescription() + HTMLBREAK);
		}
		return (title + s2);
	}	
}
