package com.jcrawley.reactivebeerclient.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
		BeerDto beerDto = BeerDto.builder()
				.beerName("Dogfishead 90 MMin IPA")
				.beerStyle("IPA")
				.upc("23452342342349")
				.price(new BigDecimal("8.99"))
				.build();
		
		Mono<ResponseEntity<Void>> responseEntityMono = beerClient.createBeer(beerDto);
		var responseEntity = responseEntityMono.block();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);	
	}

	
	@Test
	void updateBeer() {
		BeerDto beerDto = getFirstBeer();
		BeerDto updatedBeer = BeerDto.builder()
				.beerName("Really Good Beer")
				.beerStyle(beerDto.getBeerStyle())
				.price(beerDto.getPrice())
				.upc(beerDto.getUpc())
				.build();
		
		Mono<ResponseEntity<Void>> responseEntityMono = beerClient.updateBeer(beerDto.getId(), updatedBeer);
		
		var responseEntity = responseEntityMono.block();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	
	private BeerDto getFirstBeer() {
		Mono<BeerPagedList> beerList = beerClient.listBeers(1, 4, null, null, false);
		BeerDto beerFromList = beerList.block().get().findFirst().get();
		Mono<BeerDto> beerById = beerClient.getBeerById(beerFromList.getId(), false);
		return beerById.block();
	}

	
	@Test
	@Disabled
	void deleteBeerById() {
		BeerDto beer = getFirstBeer();
		Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(beer);
		assertThat(responseEntityMono.block().getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	
	@Test
	void deleteBeerByIdNotFound() {
		BeerDto beer = getFirstBeer();
		beer.setId(new UUID(99999, 99999));
		Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(beer);
		assertThrows(WebClientResponseException.class, () -> {
			ResponseEntity<Void> responseEntity = responseEntityMono.block();
			
		});
		
		//assertThat(responseEntityMono.block().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	
	/*
	 * 
	 *  would be using this kind of code more in application code, rather than testing
	 * 
	 */
	@Test
	void testDeleteBeerHandleException() {
		BeerDto beerDto = getFirstBeer();
		beerDto.setId(UUID.randomUUID());
		Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(beerDto);
		
		ResponseEntity<Void> responseEntity = responseEntityMono.onErrorResume(throwable ->
		{
			if(throwable instanceof WebClientResponseException) {
				WebClientResponseException exception = (WebClientResponseException)throwable;
				return Mono.just(ResponseEntity.status(exception.getStatusCode()).build());
			}else {
				throw new RuntimeException(throwable);
			}
		}).block();
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	

	@Test
	void functionalTestGetBeerById() throws InterruptedException {
		AtomicReference<String> beerName = new AtomicReference<>();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		beerClient.listBeers(null, null, null, null, false)
		.map(beerPagedList -> beerPagedList.getContent().get(0).getId())
		.map(beerId -> beerClient.getBeerById(beerId, false))
				.flatMap(mono -> mono)
				.subscribe(beerDto -> {
					beerName.set(beerDto.getBeerName());
					System.out.println("**** beerName: " + beerDto.getBeerName());
					countDownLatch.countDown();
				});
		countDownLatch.await();
		assertThat(beerName.get()).isEqualTo("Mango Bobs");
	}
	
}
