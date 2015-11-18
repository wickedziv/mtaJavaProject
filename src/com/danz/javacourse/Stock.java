package com.danz.javacourse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author  A KAKI :D :))))
 * Exercise 04
 */

public class Stock {
	private String symbol;
	private float ask, bid;
	private Date date = new Date();
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public Stock(String symbol, float ask, float bid, Date date) {
		setSymbol(symbol);
		setAsk(ask);
		setBid(bid);
		setDate(date);
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

	public String getHtmlDescription(){
		String returnString = (" <b> Stock symbol: </b> " + getSymbol() + " <b> ask: </b> " + getAsk() + " <b> bid: </b> " + getBid() + " <b> date: </b> " + sdf.format(getDate()));
		return returnString;
	}
	
}
