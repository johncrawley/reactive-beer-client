package com.jcrawley.reactivebeerclient.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jcrawley.reactivebeerclient.config.WebClientConfig;
import com.jcrawley.reactivebeerclient.model.BeerDto;
import com.jcrawley.reactivebeerclient.model.BeerPagedList;

import reactor.core.publisher.Mono;


public class BeerClientImplTests {

	BeerClientImpl beerClient;
	
	@BeforeEach
	void setUp() {
		
		beerClient = new BeerClientImpl(new WebClientConfig().webClient());
	}
	
	

	@Test
	void listBeers() {
		Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null, false);
	
		BeerPagedList pagedList = beerPagedListMono.block();
		assertThat(pagedList).isNotNull();
		assertThat(pagedList.getContent().size()).isGreaterThan(0);
	}
	
	
	@Test
	void listBeersPageSize10() {
		Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10, null, null, false);
	
		BeerPagedList pagedList = beerPagedListMono.block();
		assertThat(pagedList).isNotNull();
		assertThat(pagedList.getContent().size()).isEqualTo(10);
	}
	
	
	@Test
	void listBeersNoRecords() {
		// if we go to page 10 and list 20, we know there will be no records currently
		Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(10, 20, null, null, false);
	
		BeerPagedList pagedList = beerPagedListMono.block();
		assertThat(pagedList).isNotNull();
		assertThat(pagedList.getContent().size()).isEqualTo(0);
	}
	

	@Test
	void getBeerById() {
		Mono<BeerPagedList> beerList = beerClient.listBeers(1, 4, null, null, false);
		BeerDto beerFromList = beerList.block().get().findFirst().get();
		Mono<BeerDto> beerById = beerClient.getBeerById(beerFromList.getId(), false);
		BeerDto retrievedBeer = beerById.block();
		assertThat(retrievedBeer.getId()).isEqualTo(beerFromList.getId());	
		assertThat(retrievedBeer.getQuantityOnHand()).isNull();	
	}

	
	@Test
	void getBeerByIdAndShowQuantityOnHand() {
		Mono<BeerPagedList> beerList = beerClient.listBeers(1, 4, null, null, false);
		BeerDto beerFromList = beerList.block().get().findFirst().get();
		Mono<BeerDto> beerById = beerClient.getBeerById(beerFromList.getId(), true);
		BeerDto retrievedBeer = beerById.block();
		assertThat(retrievedBeer.getId()).isEqualTo(beerFromList.getId());	
		assertThat(retrievedBeer.getQuantityOnHand()).isNotNull();	
	}
	
	
	@Test
	void getBeerByUpc() {
		Mono<BeerPagedList> beerList = beerClient.listBeers(1, 4, null, null, false);
		BeerDto beerFromList = beerList.block().get().findFirst().get();
		Mono<BeerDto> beerById = beerClient.getBeerByUPC(beerFromList.getUpc());
		BeerDto retrievedBeer = beerById.block();
		assertThat(retrievedBeer.getId()).isEqualTo(beerFromList.getId());	
	}
	
	
	@Test
	void createBeer() {
		
	}

	@Test
	void updateBeer() {
	
	}

	@Test
	void deleteBeerById() {
	
	}

	@Test
	void getBeerByUPC() {
		
	}
}
