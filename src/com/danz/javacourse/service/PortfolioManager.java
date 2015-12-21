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

import com.danz.javacourse.exception.BalanceException;
import com.danz.javacourse.exception.NotEnoguhStocks;
import com.danz.javacourse.exception.StockAlreadyExistsException;
import com.danz.javacourse.exception.StockNotExistException;
import com.danz.javacourse.model.Portfolio;
import com.danz.javacourse.model.Stock;
import com.danz.javacourse.model.Stock.ALGO_RECOMMENDATION;

public class PortfolioManager implements PortfolioManagerInterface {
	private DatastoreService dataStoreService = ServiceManager.datastoreService();
	
	public PortfolioManager(){
	}
	
	@Override
	/**
	 * Returns portfolio from GAN database
	 * @return portfolio Portfolio from dto
	 */
	public PortfolioInterface getPortfolio() {
		PortfolioDto portfolioDto = dataStoreService.getPortfolilo();
		return fromDto(portfolioDto);
	}
	
	/**
	 * Update portfolio with stocks
	 * @throws SymbolNotFoundInNasdaq If symbol wasn't found in Yahoo nasdaq
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
	/**
	 * Set's portfolio title and stores it to GAN db
	 */
	public void setTitle(String title) {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.setTitle(title);
		flush(portfolio);
	}

	@Override
	/**
	 * Updates portfolio's balance and stores it to GAN db
	 */
	public void updateBalance(float value) throws BalanceException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.updateBalance(value);
		flush(portfolio);
	}

	@Override
	/**
	 * Returns portfolio status from DB (history and current) and sorts it by date (asc)
	 * @return ret Portfolio total status array  */
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
	public void addStock(String symbol) throws StockAlreadyExistsException {
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
	/**
	 * Buys stock with received quantity and stores it in DB*/
	public void buyStock(String symbol, int quantity) throws BalanceException, StockAlreadyExistsException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.buyStock(portfolio.findStock(symbol), quantity);
		flush(portfolio);
	}

	@Override
	/**
	 * Sells stock with received quantity and stores it in DB*/
	public void sellStock(String symbol, int quantity) throws BalanceException, NotEnoguhStocks {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.sellStock(symbol, quantity);
		flush(portfolio);
	}

	@Override
	/**
	 * Removes stock from portfolio and stores it in DB*/
	public void removeStock(String symbol) throws BalanceException, NotEnoguhStocks, StockNotExistException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.removeStock(symbol);
		flush(portfolio);
	}
	
	/**
	 * Stores portfolio in db */
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
			newStock.setRecommendation(ALGO_RECOMMENDATION.valueOf(stockDto.getRecommendation()));
//		newStock.setRecommendation(stockDto.getRecommendation())

		return newStock;
	}
	
	/**
	 * toDto - Convert stock to StockDto Object
	 * @param inStock Stock
	 * @return dto StockDto object
	 */
	private StockDto toDto(StockInterface inStock) {
		if (inStock == null) {
			return null;
		}
		Stock stock = (Stock) inStock;
		StockDto dto = new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(), 
				stock.getDate(), stock.getStockQuantity(), stock.getRecommendation().name());
		return dto;
	}

	/**
	 * toDto - converts Portfolio to Portfolio DTO
	 * @param portfolio Portfolio to convert to Dto
	 * @return PortfolioDto Converted PortfolioDto
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
	 * @param dto PortfolioDto object
	 * @return portfolio Portfolio converted from dto
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
	 * @param stocks Stocks to convert to Dto
	 * @return stockDto converted StockDTO
	 */
	private List<StockDto> toDtoList(List<Stock> stocks) {
		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}	
}
