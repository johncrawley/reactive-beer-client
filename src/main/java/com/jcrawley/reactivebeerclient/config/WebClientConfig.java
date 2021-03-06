package com.jcrawley.reactivebeerclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl(WebClientProperties.BASE_URL)
				.clientConnector(new ReactorClientHttpConnector(HttpClient.create()
						.wiretap(true)))
				.build();
		
		//.wiretap("reactor.netty.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
	}
}
