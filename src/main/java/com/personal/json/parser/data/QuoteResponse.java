package com.personal.json.parser.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuoteResponse {
	@JsonProperty("quote")
	private List<Quote> quotes;
}