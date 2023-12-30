package com.personal.json.parser.config;

import java.io.IOException;
import java.net.URL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.json.parser.data.QuoteResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JsonLoader {

	private ObjectMapper objectMapper;

	@Lazy
	public JsonLoader(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public QuoteResponse loadQuotes(final String filepath) throws IOException {
		log.info("Parsing file from a given path: {}", filepath);
		final URL url = new URL(filepath);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.readValue(url, QuoteResponse.class);
	}

	@Bean
	ObjectMapper createObjectMapper() {
		return new ObjectMapper();
	}
}
