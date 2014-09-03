package com.hangfu.foodtruck.webservices.config;

import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.AnnotationIntrospector.Pair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JAXBContextResolver implements ContextResolver<ObjectMapper> {
	private ObjectMapper objectMapper;

	public JAXBContextResolver() throws Exception {
		this.objectMapper = new ObjectMapper();

		Pair combinedIntrospector = createJaxbJacksonAnnotationIntrospector();

		if (Boolean.getBoolean(System.getProperty("indent"))) {
			objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		}
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

		objectMapper.setDeserializationConfig(objectMapper.getDeserializationConfig().withAnnotationIntrospector(
				combinedIntrospector));
		objectMapper.setSerializationConfig(objectMapper.getSerializationConfig().withAnnotationIntrospector(
				combinedIntrospector));
		objectMapper.setDateFormat(new SimpleDateFormat("yyyyMMdd"));

		objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	/**
	 * JAXB Introspector
	 */
	private Pair createJaxbJacksonAnnotationIntrospector() {
		AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector();
		AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();

		return new AnnotationIntrospector.Pair(jacksonIntrospector, jaxbIntrospector);
	}

	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}
}