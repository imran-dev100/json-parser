package com.personal.json.parser.data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Quote {

	private String market;

	@JsonProperty("date_applied")
	private Date dateApplied;

	@JsonProperty("values")
	private List<QuoteValue> quoteValues;
}