package com.jcrawley.reactivebeerclient.client;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jcrawley.reactivebeerclient.config.WebClientProperties;
import com.jcrawley.reactivebeerclient.model.BeerDto;
import com.jcrawley.reactivebeerclient.model.BeerPagedList;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

	private final WebClient webClient;
	
	
	@Override
	public Mono<BeerDto> getBeerById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle,
			boolean showInventoryOnhand) {
		return webClient.get().uri(WebClientProperties.BEER_V1_PATH)
		.retrieve().bodyToMono(BeerPagedList.class);
	
	}

	@Override
	public Mono<ResponseEntity<BeerDto>> createBeer(BeerDto beerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ResponseEntity<BeerDto>> updateBeer(BeerDto beerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ResponseEntity<BeerDto>> deleteBeer(BeerDto beerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<BeerDto> getBeerByUPC(String upc) {
		// TODO Auto-generated method stub
		return null;
	}

}
