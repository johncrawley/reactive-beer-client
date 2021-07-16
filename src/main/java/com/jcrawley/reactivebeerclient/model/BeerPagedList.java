package com.jcrawley.reactivebeerclient.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

// NB left out Serializable interface, it may be required, but Im not sure why
public class BeerPagedList extends PageImpl<BeerDto>{

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public BeerPagedList(@JsonProperty("content") List<BeerDto> content,
			@JsonProperty("number") int number,
			@JsonProperty("size") int size,
			@JsonProperty("totalElements") Long totalElements,
			@JsonProperty("pageable") JsonNode pageable,
			@JsonProperty("last") boolean last,
			@JsonProperty("totalPages") int totalPages,
			@JsonProperty("sort") JsonNode sort,
			@JsonProperty("first") boolean first,
			@JsonProperty("numberOfElements") int numberOfElements) {
	
		super(content, PageRequest.of(numberOfElements,  size), totalElements);
	}
	
	public BeerPagedList(List<BeerDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}
	
	public BeerPagedList(List<BeerDto> content) { super(content);}
}
