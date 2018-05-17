package com.jfdh.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomStatusSerializer extends JsonSerializer<Boolean> {
	@Override
	public void serialize(Boolean value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeString(value.booleanValue()?"已使用":"未使用");
	}
}
