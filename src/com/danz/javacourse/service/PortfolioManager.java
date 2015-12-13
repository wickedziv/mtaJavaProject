package com.danz.javacourse.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.algo.dto.PortfolioDto;
import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;
import org.algo.service.DatastoreService;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;
import org.algo.service.ServiceManager;

import com.danz.javacourse.model.Portfolio;
import com.danz.javacourse.model.Stock;
//import com.danz.javacourse.model.Stock.ALGO_RECOMMENDATION;

public class PortfolioManager implements PortfolioManagerInterface {
	private DatastoreService dataStoreService = ServiceManager.datastoreService();
	
	public PortfolioManager(){
	}
	
	public PortfolioInterface getPortfolio() {
		PortfolioDto portfolioDto = dataStoreService.getPortfolilo();
		return fromDto(portfolioDto);
	}
	
	/**
	 * Update portfolio with stocks
	 */
	@Override
	public void update() {
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.getMaxSize());
		for (StockInterface si : stocks) {
			symbols.add(si.getSymbol());
		}

		List<Stock> update = new ArrayList<>(Portfolio.getMaxSize());
		List<Stock> currentStocksList = new ArrayList<Stock>();
		try {
			List<StockDto> stocksList = MarketService.getInstance().getStocks(symbols);
			for (StockDto stockDto : stocksList) {
				Stock stock = fromDto(stockDto);
				currentStocksList.add(stock);
			}

			for (Stock stock : currentStocksList) {
				update.add(new Stock(stock));
			}

			dataStoreService.saveToDataStore(toDtoList(update));

		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void setTitle(String title) {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.setTitle(title);
		flush(portfolio);
	}

	@Override
	public void updateBalance(float value) throws PortfolioException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.updateBalance(value);
		flush(portfolio);
	}

	@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus () {
		Portfolio portfolio = (Portfolio) getPortfolio();
		Map<Date, Float> map = new HashMap<>();

		//get stock status from db.
		StockInterface[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			StockInterface stock = stocks[i];

			if(stock != null) {
				List<StockDto> stockHistory = null;
				try {
					stockHistory = dataStoreService.getStockHistory(stock.getSymbol(),30);
				} catch (Exception e) {
					return null;
				}
				for (StockDto stockDto : stockHistory) {
					Stock stockStatus = fromDto(stockDto);
					float value = stockStatus.getBid()*stockStatus.getStockQuantity();

					Date date = stockStatus.getDate();
					Float total = map.get(date);
					if(total == null) {
						total = value;
					}else {
						total += value;
					}

					map.put(date, value);
				}
			}
		}

		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];

		int index = 0;
		//create dto objects
		for (Date date : map.keySet()) {
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}

		//sort by date ascending.
		Arrays.sort(ret);

		return ret;
	}


	@Override
	public void addStock(String symbol) {
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			//get current symbol values from nasdaq.
			StockDto stockDto = ServiceManager.marketService().getStock(symbol);
			Stock stock = fromDto(stockDto);
			
			//first thing, add it to portfolio.
			portfolio.addStocks(stock);
			
			//second thing, save the new stock to the database.
			dataStoreService.saveStock(toDto(portfolio.findStock(symbol)));
			
			flush(portfolio);
		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println("Stock Not Exists: "+symbol);
		}
	}

	@Override
	public void buyStock(String symbol, int quantity) throws PortfolioException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		Stock s1 = new Stock(portfolio.findStock(symbol));
		portfolio.buyStock(s1, quantity);
		flush(portfolio);
	}

	@Override
	public void sellStock(String symbol, int quantity) throws PortfolioException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.sellStock(symbol, quantity);
		flush(portfolio);
	}

	@Override
	public void removeStock(String symbol) throws PortfolioException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.removeStock(symbol);
		flush(portfolio);
	}
	
	private void flush(Portfolio portfolio) {
		dataStoreService.updatePortfolio(toDto(portfolio));
	}

	/**
	 * fromDto - get stock from Data Transfer Object
	 * @param stockDto
	 * @return Stock
	 */
	private Stock fromDto(StockDto stockDto) {
		Stock newStock = new Stock();

		newStock.setSymbol(stockDto.getSymbol());
		newStock.setAsk(stockDto.getAsk());
		newStock.setBid(stockDto.getBid());
		newStock.setDate(stockDto.getDate());
		newStock.setStockQuantity(stockDto.getQuantity());
		if (stockDto.getRecommendation() != null)
//			newStock.setRecommendation(ALGO_RECOMMENDATION.valueOf(stockDto.getRecommendation()));
		newStock.setRecommendation(stockDto.getRecommendation())
		;

		return newStock;
	}
	
	private StockDto toDto(StockInterface inStock) {
		if (inStock == null) {
			return null;
		}
		Stock stock = (Stock) inStock;
		StockDto dto = new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(), 
				stock.getDate(), stock.getStockQuantity(), stock.getRecommendation());
		return dto;
	}

	/**
	 * toDto - converts Portfolio to Portfolio DTO
	 * @param portfolio
	 * @return
	 */
	private PortfolioDto toDto(Portfolio portfolio) {
		StockDto[] array = null;
		StockInterface[] stocks = portfolio.getStocks();
		if(stocks != null) {
			array = new StockDto[stocks.length];
			for (int i = 0; i < stocks.length; i++) {

				array[i] = toDto(stocks[i]);
			}
		}
		return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
	}

	/**
	 * fromDto - converts portfolioDto to Portfolio
	 * @param dto
	 * @return portfolio
	 */
	private Portfolio fromDto(PortfolioDto dto) {
		StockDto[] stocks = dto.getStocks();
		Portfolio ret;
		if(stocks == null) {
			ret = new Portfolio();			
		}
		else {
			List<Stock> stockList = new ArrayList<Stock>();
			for (StockDto stockDto : stocks) {
				stockList.add(fromDto(stockDto));
			}

//			Stock[] stockArray = stockList.toArray(new Stock[stockList.size()]);
			ret = new Portfolio(stockList);
		}

		ret.setTitle(dto.getTitle());
		try {
			ret.updateBalance(dto.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}	

	/**
	 * toDtoList - convert List of Stocks to list of Stock DTO
	 * @param stocks
	 * @return stockDto
	 */
	private List<StockDto> toDtoList(List<Stock> stocks) {
		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}	
}
