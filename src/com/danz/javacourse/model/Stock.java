/**
 * @author Dan Roujinsky & Ziv Rubin
 * Stock class is the base for all stock contexts.
 */
package com.danz.javacourse.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.algo.model.StockInterface;


public class Stock implements StockInterface {
	private String symbol;
	private float ask;
	private float bid;
	private Date date = new Date();
	private int stockQuantity;
//	private ALGO_RECOMMENDATION recommendation = ALGO_RECOMMENDATION.BUY;
	private String recommendation;
		
	public Stock(){
	}
	
	public Stock(String symbol, float ask, float bid, Date date) {
		setSymbol(symbol);
		setAsk(ask);
		setBid(bid);
		setDate(date);
	}
	
	/**
	 * Copy constructor - copies received stock object
	 * @param stock Stock object to copy
	*/
	public Stock(StockInterface stock) { // copy constructor
		this(stock.getSymbol(), stock.getAsk(), stock.getBid(), stock.getDate());
		Date tempDate = new Date(stock.getDate().getTime());
		setDate(tempDate);
		setStockQuantity(((Stock) stock).getStockQuantity());
	}

	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public float getAsk() {
		return ask;
	}
	
	public void setAsk(float ask) {
		this.ask = ask;
	}
	
	public float getBid() {
		return bid;
	}
	
	public void setBid(float bid) {
		this.bid = bid;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public synchronized int getStockQuantity() {
		return stockQuantity;
	}

	public synchronized void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	/**
	 * Returns string containing all stock members
	 * @return	String containing Stock data members 
	*/
	public String getHtmlDescription(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String returnString = (" <b> Stock symbol: </b> " + getSymbol() + " <b> ask: </b> " + getAsk() + " <b> bid: </b> " + getBid() + " <b> date: </b> " + sdf.format(getDate()) + " <b> quantity: </b>" + getStockQuantity());
		return returnString;
	}
	
}
