/**
 * @author Dan
 * @version 6
 * Portfolio contains a Stock Object ArrayList and a title (String).
 */

package com.danz.javacourse.model;

import java.util.ArrayList;

public class Portfolio {
	private static final String HTMLBREAK = ("<br></br>");
	private String title = new String();
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	
	
	public Portfolio(){

	}
	
	/**
	 * Copy constructor. Receives stock array list and title.
	 * @param stocks Array List of Stocks
	 * @param title String of Portfolio Title
	*/
	public Portfolio(ArrayList<Stock> stocks, String title){  // copy constructor 
		this.stocks = new ArrayList<Stock>(stocks);
		this.title = title;		
	}
	
	/**
	 * Copy constructor. Receives Portfolio object.
	 * Creates a new Array list of stocks and clone's received Portfolio's stocks and sets title.
	 * @param userPortfolio Portfolio object to copy
	*/
	public Portfolio(Portfolio userPortfolio){ // copy constructor
		ArrayList<Stock> temp = new ArrayList<Stock>(userPortfolio.stocks.size());
		for(Stock s: userPortfolio.stocks){
			temp.add(new Stock(s));
		}
		this.stocks = temp;
		setTitle(userPortfolio.getTitle());
	}
	
	public synchronized ArrayList<Stock> getStocks() {
		return stocks;
	}
	
	/**
	 * Deletes an element from the stocks array list
	 * @param index Element's index position
	 */
	public synchronized void deleteFromStockList(int index){
		stocks.remove(index);
	}
	
	public synchronized void setTitle(String title){
		this.title = title;
	}
	
	public synchronized String getTitle() {
		return title;
	}
	
	/**
	 * Adds stock to the array list.
	 * @param stock Stock to add to the array.
	 */
	public synchronized void addStocks(Stock stock) {
		stocks.add(stock);
	}
	
	/**
	 * Returns string containing portfolio title and all stocks in array.
	 * @return Returns string containing title and stock data
	 */
	public String getHtmlString(){
		String s1 = "";
		for(Stock stocks: stocks){
			s1 += (stocks.getHtmlDescription() + HTMLBREAK);
		}
		return (title+s1);
	}
}	
