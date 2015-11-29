package com.danz.javacourse.service;

import java.util.Date;

import com.danz.javacourse.Stock;
import com.danz.javacourse.model.Portfolio;

public class PortfolioManager {
	private Portfolio portfolio = new Portfolio();
	
	@SuppressWarnings("deprecation")
	public Portfolio getPortfolio(){
		this.portfolio.addStocks(new Stock("PIH", 13.1f, 12.4f, new Date("11/15/2015")));
		this.portfolio.addStocks(new Stock("AAL", 5.78f, 5.5f, new Date("11/15/2015")));
		this.portfolio.addStocks(new Stock("CAAS", 32.2f, 31.5f, new Date("11/14/2015")));
		return portfolio;
	}
}
