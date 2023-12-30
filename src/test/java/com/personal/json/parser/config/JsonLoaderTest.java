package com.personal.json.parser.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.personal.json.parser.data.Quote;
import com.personal.json.parser.data.QuoteResponse;
import com.personal.json.parser.data.QuoteValue;

@SpringBootTest
@ContextConfiguration(classes = JsonLoader.class)
class JsonLoaderTest {

	private static final String LOCATION = "Europe/Amsterdam";

	private static final String DATE = "date";

	private static final String HOUR = "Hour";

	private static final String NET_VOLUME = "Net Volume";

	private static final String PRICE = "Price";

	private static final String HOUR_KEY = "hour";

	private static final String NET_VOLUME_KEY = "net volume";

	private static final String PRICE_KEY = "price";

	@Autowired
	private JsonLoader jsonLoader;

	@BeforeEach
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(LOCATION));
	}

	@Test
	void testLoadQuotes() throws IOException {
		final Path resourceDirectory = Paths.get("src", "test", "resources", "apx-data.json");
		final String filepath = resourceDirectory.toFile().getAbsolutePath();
		final QuoteResponse response = jsonLoader.loadQuotes("file:".concat(filepath));
		final List<JSONObject> output = generateExpectedOutput(response);
		assertEquals(24, output.size());
		output.stream().forEach(System.out::println);
	}

	private List<JSONObject> generateExpectedOutput(final QuoteResponse response) {
		final List<JSONObject> jsonObjects = new ArrayList<>();
		response.getQuotes().stream().forEach(quote -> jsonObjects.add(processEachQuote(quote)));
		return jsonObjects;
	}

	private JSONObject processEachQuote(final Quote quote) {
		final JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(DATE, quote.getDateApplied());
			quote.getQuoteValues().stream().forEach(quoteValue -> processEachQuoteValue(jsonObject, quoteValue));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private void processEachQuoteValue(final JSONObject jsonObject, final QuoteValue quoteValue) {
		try {
			switch (quoteValue.getTLabel()) {
			case HOUR:
				jsonObject.put(HOUR_KEY, (int) quoteValue.getValue());
				break;
			case NET_VOLUME:
				jsonObject.put(NET_VOLUME_KEY, quoteValue.getValue());
				break;
			case PRICE:
				jsonObject.put(PRICE_KEY, quoteValue.getValue());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
