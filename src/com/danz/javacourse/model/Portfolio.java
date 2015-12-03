package com.danz.javacourse.model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
	private static final String
//	ERROR_MAXPORT_SIZE = ("Max Portoflio Size Exceeded!"),
	HTMLBREAK = ("<br></br>");
	private String title = new String();
	//private static final int MAX_PORTFOLIO_SIZE = 5;
	//private Stock[] stocks;
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	//private int portfolioSize = 0;
	
	public Portfolio(){
//		stocks = new Stock[MAX_PORTFOLIO_SIZE];
	}
	
	public Portfolio(ArrayList<Stock> stocks, String title){  // copy constructor 
		this.stocks = new ArrayList<Stock>(stocks);
//		this.portfolioSize = portfolioSize;
		this.title = title;		
	}
	
	public Portfolio(Portfolio userPortfolio){ // copy constructor
		ArrayList<Stock> temp = new ArrayList<Stock>(userPortfolio.stocks.size());
		for(Stock s: userPortfolio.stocks){
			temp.add(new Stock(s));
		}
		this.stocks = temp;
		setTitle(userPortfolio.title);
		
//		this(userPortfolio.getStocks(), 
//				//userPortfolio.getPortfolioSize(), 
//				userPortfolio.getTitle());
	}
	
	public synchronized ArrayList<Stock> getStocks() {
		return stocks;
	}
	
	public synchronized void deleteFromStockList(int index){
		stocks.remove(index);
	}
	
	public synchronized void setTitle(String title){
		this.title = title;
	}
	
	public synchronized String getTitle() {
		return title;
	}

	public synchronized void addStocks(Stock stock) {
		stocks.add(stock);
//		portfolioSize++;
//		if(portfolioSize < MAX_PORTFOLIO_SIZE){
//			stocks[portfolioSize] = stock;
//			portfolioSize++;
//		}
//		else
//			this.returnError();
	}
	
//	public synchronized int getPortfolioSize(){
//		return portfolioSize;
//	}
	
//	public String returnError(){
//		return ERROR_MAXPORT_SIZE; 
//	}
		
	public String getHtmlString(){
		String s1 = "";
		for(Stock stocks: stocks){
			s1 += (stocks.getHtmlDescription() + HTMLBREAK);
		}
		return (title+s1);
	}
/*		Old (Exercise 05):
 * 		String s2 = "";
//		try{
//			for(int i=0; i < portfolioSize; i++){
//				s2 += (stocks[i].getHtmlDescription() + HTMLBREAK);
//			}
//		}
//		catch(NullPointerException e){
//			s2 += ("");
//		}
//		return (title + s2); */
	}	
