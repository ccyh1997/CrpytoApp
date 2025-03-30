package com.aquariux.assessment.cryptoapp.service.impl;

import com.aquariux.assessment.cryptoapp.model.Crypto;
import com.aquariux.assessment.cryptoapp.repository.CryptoRepository;
import com.aquariux.assessment.cryptoapp.service.PriceService;
import com.aquariux.assessment.cryptoapp.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.aquariux.assessment.cryptoapp.util.Constants.BTCUSDT;
import static com.aquariux.assessment.cryptoapp.util.Constants.ETHUSDT;

@Service
public class PriceServiceImpl implements PriceService {
    private static final Logger logger = LogManager.getLogger(PriceServiceImpl.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CryptoRepository cryptoRepository;

    public PriceServiceImpl(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Scheduled(fixedRate = 10000)
    @Override
    public void aggregatePrices() {
        List<String> cryptoTickers = Arrays.asList(BTCUSDT, ETHUSDT);
        try {
            logger.info(Constants.ASTERISKS);
            cryptoTickers.forEach(cryptoTicker -> {
                try {
                    // Binance
                    String binanceUrl = "https://api.binance.com/api/v3/ticker/bookTicker?symbol=" + cryptoTicker;
                    String binanceResponse = restTemplate.getForObject(binanceUrl, String.class);
                    JsonNode binanceNode = objectMapper.readTree(binanceResponse);
                    BigDecimal binanceBidPrice = new BigDecimal(binanceNode.get("bidPrice").asText());
                    BigDecimal binanceAskPrice = new BigDecimal(binanceNode.get("askPrice").asText());

                    // Huobi
                    String huobiUrl = "https://api.huobi.pro/market/tickers";
                    String huobiResponse = restTemplate.getForObject(huobiUrl, String.class);
                    JsonNode huobiNode = objectMapper.readTree(huobiResponse);
                    BigDecimal huobiBidPrice = BigDecimal.ZERO;
                    BigDecimal huobiAskPrice = BigDecimal.ZERO;
                    for (JsonNode data : huobiNode.get("data")) {
                        if (cryptoTicker.toLowerCase().equals(data.get("symbol").asText())) {
                            huobiBidPrice = new BigDecimal(data.get("bid").asText());
                            huobiAskPrice = new BigDecimal(data.get("ask").asText());
                            break;
                        }
                    }

                    // Aggregated Prices
                    BigDecimal bestAggregatedBid = binanceBidPrice.max(huobiBidPrice); // higher bid = better for sellers
                    BigDecimal bestAggregatedAsk = binanceAskPrice.min(huobiAskPrice); // lower ask = better for buyers

                    // Log the prices
                    logger.info("Binance Bid Price for {}: {}", cryptoTicker, binanceBidPrice);
                    logger.info("Binance Ask Price for {}: {}", cryptoTicker, binanceAskPrice);
                    logger.info("Huobi Bid Price for {}: {}", cryptoTicker, huobiBidPrice);
                    logger.info("Huobi Ask Price for {}: {}", cryptoTicker, huobiAskPrice);
                    logger.info("Best Aggregated Bid for {}: {}", cryptoTicker, bestAggregatedBid);
                    logger.info("Best Aggregated Ask for {}: {}", cryptoTicker, bestAggregatedAsk);

                    // Save prices into database
                    Crypto crypto = getCrypto(cryptoTicker, bestAggregatedBid, bestAggregatedAsk);
                    cryptoRepository.save(crypto);
                } catch (Exception e) {
                    logger.error("Error fetching prices for {}: {}", cryptoTicker, e.getMessage(), e);
                }
            });
        } catch (Exception e) {
            logger.error("Error processing tickers: {}", e.getMessage(), e);
        }
    }

    private Crypto getCrypto(String cryptoTicker, BigDecimal bestAggregatedBid, BigDecimal bestAggregatedAsk) {
        Long cryptoId = cryptoTicker.equals(BTCUSDT) ? 1L : 2L;
        String cryptoName = cryptoTicker.equals(BTCUSDT) ? "Bitcoin" : "Ethereum";
        Crypto crypto = new Crypto();
        crypto.setCryptoId(cryptoId);
        crypto.setCryptoName(cryptoName);
        crypto.setCryptoTicker(cryptoTicker);
        crypto.setBidPrice(bestAggregatedBid);
        crypto.setAskPrice(bestAggregatedAsk);
        return crypto;
    }
}