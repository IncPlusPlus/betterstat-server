package io.github.incplusplus.thermostat.server.config;

import springfox.documentation.service.Tag;

public final class EndpointTags
{
	public static final Tag[] ALL_ENDPOINT_TAGS = {
			/*
           * This tag is here for the express purpose of being an early warning system
           * for documentation and/or licensing code not working correctly.
           * If this tag is showing up in the Swagger docs, it means that something
           * is wrong with conditionallyAddTags() in SwaggerConfig or there is an issue
           * at a lower level in a method that conditionallyAddTags() uses.
           */
			new Tag("neverShouldBeDisplayed", "THIS TAG SHOULD NOT BE SHOWING ON SWAGGER-UI! Check SwaggerConfig to see what broke."),
	};
}
