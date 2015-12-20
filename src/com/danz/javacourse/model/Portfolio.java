/**
 * @author Dan
 * @version 7
 * Portfolio contains a Stock Object ArrayList and a title (String).
 */

package com.danz.javacourse.model;

import java.util.ArrayList;
import java.util.List;

import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;


public class Portfolio implements PortfolioInterface {
	private static final String HTMLBREAK = ("<br></br>");
	private static final int MAX_PORTFOLIO_SIZE = 5;
	private float balance;
	private String title = new String();
	private ArrayList<StockInterface> stocks = new ArrayList<StockInterface>();
	
	public Portfolio(){
	}
	
	/**
	 * Copy constructor. Receives stock array list and title.
	 * @param stocks Array List of Stocks
	 * @param title String of Portfolio Title
	 * @param balance Portfolio balance
	*/
	public Portfolio(ArrayList<StockInterface> stocks, String title, float balance){  // copy constructor
		ArrayList<StockInterface> temp = new ArrayList<StockInterface>(stocks.size());
		for(StockInterface s: stocks){
			temp.add(new Stock(s));
		}
		this.stocks = temp;
		setTitle(title);
		setBalance(balance);
	}

	/**
	 * Copy constructor. Receives Portfolio object.
	 * Creates a new Array list of stocks and clone's received Portfolio's stocks and sets title.
	 * @param userPortfolio Portfolio object to copy
	*/
	public Portfolio(Portfolio userPortfolio){ // copy constructor
		this(userPortfolio.stocks, userPortfolio.title, userPortfolio.balance);
	}
	
//	public synchronized ArrayList<StockInterface> getStocks() {
//		return stocks;
//	}
	
	public Portfolio(List<Stock> stockList) {
		ArrayList<StockInterface> temp = new ArrayList<StockInterface>(stockList.size());
		for(StockInterface s: stockList){
			temp.add(new Stock(s));
		}
		this.stocks = temp;
	}

	public synchronized StockInterface[] getStocks(){
		StockInterface[] stockArray = new StockInterface[stocks.size()];
		stocks.toArray(stockArray);
		return stockArray;
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
	public synchronized void addStocks(StockInterface stock) {
		boolean addStock = false;
		if(this.stocks.size() == 0){
			((Stock) stock).setStockQuantity(0);
			stocks.add(stock);
		}
		else{
			for(StockInterface s: this.stocks){
				if(s.getSymbol().equals(stock.getSymbol())){
					break;
				}
				else
					addStock=true;
			}
		}
		if(addStock){
			((Stock) stock).setStockQuantity(0);
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
		for (StockInterface s: this.stocks) {
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
		for (StockInterface s: this.stocks){
			if (s.getSymbol().equals(symbol)){
				if(quantity > ((Stock) s).getStockQuantity()){
					System.out.println("Not enough stocks to sell");
					success = true;
					break;
				}
				else if(quantity == -1){
					updateBalance(((Stock) s).getStockQuantity() * s.getBid());
					((Stock) s).setStockQuantity(0);
					break;
				}
				else{
					((Stock) s).setStockQuantity(((Stock) s).getStockQuantity() - quantity);
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
		for (StockInterface s: this.stocks){
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
					((Stock) s).setStockQuantity(((Stock) s).getStockQuantity() + quantity);
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
		for(StockInterface s: this.stocks){
			stocksValue += (((Stock) s).getStockQuantity() * s.getBid());
		}
		return stocksValue;
	}
	
	public synchronized void setBalance(float balance) {
		this.balance = balance;
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
	
	public static int getMaxSize(){
		return MAX_PORTFOLIO_SIZE;
	}
	
	public Stock findStock(String symbol){
		Stock s1 = new Stock();
		for(StockInterface s: this.stocks){
			if(s.getSymbol().equals(symbol)){
				s1 = (Stock) s;
				break;
			}
		}
		return s1;
	}
	
	
	public String getHtmlString(){
		String s1 = "";
		String s2 = ("Total Portfolio Value: " 	+  	getTotalValue() 	+ "$" + HTMLBREAK +
					 "Total Stocks Value: " 	+ 	getStocksValue() 	+ "$" + HTMLBREAK +
					 "Balance: " 				+ 	getBalance() 		+ "$");
		for(StockInterface stocks: stocks){
			s1 += (((Stock) stocks).getHtmlDescription() + HTMLBREAK);
		}
		return (title + s1 + s2);
	}
}	
