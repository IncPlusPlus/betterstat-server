package io.github.incplusplus.thermostat.server.beans;

import io.github.incplusplus.thermostat.server.config.EndpointTags;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.service.Tag;

import java.util.ArrayList;
import java.util.List;

@Component
public class Tags
{
	@Autowired
	private ListableBeanFactory listableBeanFactory;
	
	@Bean
	public static springfox.documentation.service.Tag[] getTags() {
		List<String> tagsDeclaredInControllers = new ArrayList<>();
		// Get all possible tags
		Tag[] tags = EndpointTags.ALL_ENDPOINT_TAGS;
		
		// Get all beans annotated with @RestController
//		listableBeanFactory.getBeansWithAnnotation(RestController.class)
				// Get all of the values from the <K,V> pairs. These values are Objects with the @Api annotation
//				.values()
				// Put it in a stream
//				.stream()
				// Filter it to only contain classes annotated with @Api
				// (just in case another class gets annotated with @RestController for some other reason)
//				.filter(licensedController -> licensedController.getClass()
//						.isAnnotationPresent(Api.class))
				// Get the tags from each endpoint class (this is a String[] at the moment)
//				.map(endpoint -> endpoint.getClass().getAnnotation(Api.class).tags())
				// Collect every one of the tag names from every controller in tagsDeclaredInControllers
//				.forEach(t -> tagsDeclaredInControllers.addAll(Arrays.asList(t)));
		// Okay. We're done with the crazy stream. I apologize for that eyesore.
//		Tag[] applicableTags;
//		applicableTags = Stream.of(tags).filter(tag -> tagsDeclaredInControllers.contains(tag.getName())).toArray(Tag[]::new);
		return tags;
	}
}
