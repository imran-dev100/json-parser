package com.personal.json.parser.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuoteValue {
	@JsonProperty("tLabel")
	private String tLabel;
	private double value;
}
