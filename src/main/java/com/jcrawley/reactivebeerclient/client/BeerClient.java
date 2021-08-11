package com.jcrawley.reactivebeerclient.client;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.jcrawley.reactivebeerclient.model.BeerDto;
import com.jcrawley.reactivebeerclient.model.BeerPagedList;

import reactor.core.publisher.Mono;

public interface BeerClient {

	
	Mono<BeerDto> getBeerById(UUID id, boolean showInventoryOnHand);
	Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, 
			String beerName, String beerStyle, boolean showInventoryOnhand);
	Mono<ResponseEntity<Void>> createBeer(BeerDto beerDto);
	Mono<ResponseEntity<Void>> updateBeer(UUID beerId, BeerDto beerDto);
	Mono<ResponseEntity<Void>> deleteBeer(BeerDto beerDto);
	Mono<BeerDto> getBeerByUPC(String upc);
	
}
