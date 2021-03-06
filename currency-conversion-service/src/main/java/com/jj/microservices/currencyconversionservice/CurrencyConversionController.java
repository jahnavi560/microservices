package com.jj.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/currency-convertor/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, 
			@PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		
		//Feign - to invoke other service easily
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("from", from);
		pathVariables.put("to", to);
				
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}",
				CurrencyConversionBean.class,
				pathVariables);
		CurrencyConversionBean response = responseEntity.getBody();
		return new CurrencyConversionBean(response.getId(),from,to,quantity,response.getConversionMultiple(),
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
	
	@GetMapping("/currency-convertor-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, 
			@PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		
		CurrencyConversionBean response = proxy.getxchangeValue(from, to);
		logger.info("CurrencyConversionController---------- {}",response);
		return new CurrencyConversionBean(response.getId(),from,to,quantity,response.getConversionMultiple(),
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}

}
