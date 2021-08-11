package com.jcrawley.reactivebeerclient.client;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

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
	public Mono<BeerDto> getBeerById(UUID id, boolean showInventoryOnHand) {
		return webClient.get()
				.uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
						.queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
						.build(id)
						)
				.retrieve().bodyToMono(BeerDto.class);
	}
	

	@Override
	public Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle,
			boolean showInventoryOnhand) {
		
		return webClient.get()
				.uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH)
						.queryParamIfPresent("pageNumber",  Optional.ofNullable(pageNumber))
						.queryParamIfPresent("pageSize",  Optional.ofNullable(pageSize))
						.queryParamIfPresent("beerName",  Optional.ofNullable(beerName))
						.queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle))
						.queryParamIfPresent("showInvnetoryOnhand", Optional.ofNullable(showInventoryOnhand))
						.build()
						)
				.retrieve().bodyToMono(BeerPagedList.class);
	
	}

	@Override
	public Mono<ResponseEntity<Void>> createBeer(BeerDto beerDto) {
		return webClient.post()
				.uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH).build())
				.body(BodyInserters.fromValue(beerDto))
				.retrieve()
				.toBodilessEntity();
	}

	@Override
	public Mono<ResponseEntity<Void>> updateBeer(UUID beerId, BeerDto beerDto) {
		return webClient.put()
		.uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
				.build(beerId))
				.body(BodyInserters.fromValue(beerDto))
				.retrieve()
				.toBodilessEntity();
	}

	@Override
	public Mono<ResponseEntity<Void>> deleteBeer(BeerDto beerDto) {
		
		return webClient.delete()
				.uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
						.build(beerDto.getId()))
						.retrieve()
						.toBodilessEntity();
	}

	
	@Override
	public Mono<BeerDto> getBeerByUPC(String upc) {
		return webClient.get()
		.uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_UPC + "/" + upc).build())
				.retrieve()
				.bodyToMono(BeerDto.class);
	}

}
