package io.github.incplusplus.thermostat.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RestUtils
{
	/**
	 * Convert an object to a JSON representation. Simple as that
	 * @param originalObject
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String o2s(Object originalObject) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(originalObject);
	}
}
