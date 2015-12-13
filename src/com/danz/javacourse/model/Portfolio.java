/**
 * @author Dan
 * @version 7
 * Portfolio contains a Stock Object ArrayList and a title (String).
 */

package com.danz.javacourse.model;

import java.util.ArrayList;

public class Portfolio {
	private static final String HTMLBREAK = ("<br></br>");
	private String title = new String();
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	private float balance;
	
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
	
	/**
	 * Deletes an element from the stocks array list
	 * @param stock Stock element to remove from array
	 */
	public synchronized void deleteFromStockList(Stock stock){
		stocks.remove(stock);
	}
	
	public synchronized void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * Adds a stock to the array if it doesn't exist and sets stock's quantity to 0. If it exists, it continues.
	 * @param stock Stock to add to the array.
	 */
	public synchronized void addStocks(Stock stock) {
		boolean addStock = false;
		if(this.stocks.size() == 0){
			stock.setStockQuantity(0);
			stocks.add(stock);
		}
		else{
			for(Stock s: this.stocks){
				if(s.getSymbol().equals(stock.getSymbol())){
					break;
				}
				else
					addStock=true;
			}
		}
		if(addStock){
			stock.setStockQuantity(0);
			stocks.add(stock);
		}
	}
	
	/**
	 * Updates portfolio balance with received amount.
	 * @param amount Amount to add / substract.
	 */
	public synchronized void updateBalance(float amount) {
		if(balance + amount < 0){
			System.out.println("Balance cannot be negative");
		}
		else
			balance += amount;
	}
	
	/**
	 * Removes stock from array by symbol, if exists. If it doesn't, false is received.
	 * @param symbol Symbol of stock to remove
	 * @return success boolean - True if found and removed, false if it wasn't. 
	 */
	public synchronized boolean removeStock(String symbol){
		boolean success = false;
		int stockIndex = 0;
		for (Stock s: this.stocks) {
			if (s.getSymbol().equals(symbol)){
				success=true;
				stockIndex=stocks.indexOf(s);
			}
			else
				continue;
		}
		if(success){
			sellStock(stocks.get(stockIndex).getSymbol(), -1);
			deleteFromStockList(stockIndex);
		}
		return success;
	}
	
	/**
	 * Sells stock with requested quantity if quantity is available.
	 * Quantity = -1 to sell the whole quantity of the stock.
	 * @param symbol Symbol of stock to sell.
	 * @param quantity Quantity of stock to sell.
	 * @return success Success - true if sold, false if didn't.  
	 */
	public synchronized boolean sellStock(String symbol, int quantity) {
		boolean success = true;
		for (Stock s: this.stocks){
			if (s.getSymbol().equals(symbol)){
				if(quantity > s.getStockQuantity()){
					System.out.println("Not enough stocks to sell");
					success = true;
					break;
				}
				else if(quantity == -1){
					updateBalance(s.getStockQuantity() * s.getBid());
					s.setStockQuantity(0);
					break;
				}
				else{
					s.setStockQuantity(s.getStockQuantity() - quantity);
					updateBalance(quantity * s.getBid());
					break;
				}
			}
			else
				success = false;
		}
		return success;
	}
	
	/**
	 * Buys stock with requested quantity, if balance allows it.
	 * Quantity = -1 to buy the whole stock quantity available (according to balance).
	 * @param stock Stock to buy
	 * @param quantity Quantity of stock to buy
	 * @return success Success - true if bought, false if didn't. 
	 */
	public synchronized boolean buyStock(Stock stock, int quantity) {
		boolean success = false, addStock = false;
		for (Stock s: this.stocks){
			if (s.getSymbol().equals(stock.getSymbol())){
				addStock=true;
				if(quantity == -1) {
					int stockAmountToBuy = (int) (this.balance / s.getAsk());
					if(stockAmountToBuy == 0){
						System.out.println("Not enough balance to complete purchase");
						break;
					}
					else{
						buyStock(stock, stockAmountToBuy);
						success = true;
					}
				}
				else if(this.balance < (stock.getAsk() * quantity)) {
					System.out.println("Not enough balance to complete purchase");
					break;
				}
				else{
					s.setStockQuantity(s.getStockQuantity() + quantity);
					updateBalance((s.getAsk() * quantity) * -1);
					success = true;
				}
			}
			else{
				success=false;
				addStock=false;
			}
		}
		if(!addStock){
			addStocks(stock);
			buyStock(stock, quantity);
		}
		return success;
	}
	
	/**
	 * Returns all stocks value
	 * @return float Stocks value (of all stocks) 
	 */
	public float getStocksValue(){
		float stocksValue = 0;
		for(Stock s: this.stocks){
			stocksValue += (s.getStockQuantity() * s.getAsk());
		}
		return stocksValue;
	}
	
	public float getBalance(){
		return this.balance;
	}
	
	/**
	 * Returns all stocks values + portfolio balance.
	 * @return float Portfolio balance + all stocks value.
	 */
	public float getTotalValue(){
		return (getStocksValue() + getBalance());
	}
	
	
	/**
	 * Returns string containing portfolio title and all stocks in array.
	 * @return String Returns string containing title and stock data
	 */
	public String getHtmlString(){
		String s1 = "";
		String s2 = ("Total Portfolio Value: " +  getTotalValue() + "$" + HTMLBREAK +
					 "Total Stocks Value: " + getStocksValue() + "$" + HTMLBREAK +
					 "Balance: " + getBalance()) + "$";
		for(Stock stocks: stocks){
			s1 += (stocks.getHtmlDescription() + HTMLBREAK);
		}
		return (title + s1 + s2);
	}
}	
