package com.jcrawley.reactivebeerclient.client;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.jcrawley.reactivebeerclient.model.BeerDto;
import com.jcrawley.reactivebeerclient.model.BeerPagedList;

import reactor.core.publisher.Mono;

public class BeerClientImpl implements BeerClient {

	@Override
	public Mono<BeerDto> getBeerById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle,
			boolean showInventoryOnhand) {
		// TODO Auto-generated method stub
		return null;
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
