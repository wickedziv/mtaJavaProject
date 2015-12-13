package com.danz.javacourse.service;

//import java.util.Date;

import java.util.Date;

import com.danz.javacourse.model.Portfolio;
import com.danz.javacourse.model.Stock;

public class PortfolioManager {
	private Portfolio portfolio = new Portfolio();
	
	
	public void addStocksToPortfolio(Stock stock){
		portfolio.addStocks(stock);
	}
	
	@SuppressWarnings("deprecation")
	public Portfolio getPortfolio(){
		Portfolio myPortfolio = new Portfolio();
		myPortfolio.setTitle("<h1>Exercise 07 Portfolio</h1>");
		myPortfolio.updateBalance(10000);
		
		myPortfolio.buyStock(new Stock("PIH", 10.0f, 8.5f, new Date("12/15/2014")), 20); 
		myPortfolio.buyStock(new Stock("AAL", 30.0f, 25.5f, new Date("12/15/2014")), 30);
		myPortfolio.buyStock(new Stock("CAAS", 20.0f, 15.5f, new Date("12/15/2014")), 40);
		
		myPortfolio.sellStock("AAL", -1);
		myPortfolio.removeStock("CAAS");
		
		return myPortfolio;
	}
}
