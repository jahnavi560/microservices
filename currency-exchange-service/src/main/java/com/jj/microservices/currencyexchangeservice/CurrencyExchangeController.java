package com.jj.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	
	@Autowired
	private Environment env;
	
	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue getxchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue =exchangeValueRepository.findByFromAndTo(from,to);
		exchangeValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		logger.info("CurrencyExchangeController----------{}",exchangeValue);
		return exchangeValue;
	}
}
