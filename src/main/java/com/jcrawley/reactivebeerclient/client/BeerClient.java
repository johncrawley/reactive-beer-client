package com.jcrawley.reactivebeerclient.client;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.jcrawley.reactivebeerclient.model.BeerDto;
import com.jcrawley.reactivebeerclient.model.BeerPagedList;

import reactor.core.publisher.Mono;

public interface BeerClient {

	
	Mono<BeerDto> getBeerById(UUID id);
	Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, 
			String beerName, String beerStyle, boolean showInventoryOnhand);
	Mono<ResponseEntity<BeerDto>> createBeer(BeerDto beerDto);
	Mono<ResponseEntity<BeerDto>> updateBeer(BeerDto beerDto);
	Mono<ResponseEntity<BeerDto>> deleteBeer(BeerDto beerDto);
	Mono<BeerDto> getBeerByUPC(String upc);
	
}
