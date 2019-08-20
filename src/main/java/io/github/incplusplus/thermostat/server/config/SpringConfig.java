package io.github.incplusplus.thermostat.server.config;

import com.fasterxml.classmate.TypeResolver;
import io.github.incplusplus.thermostat.server.beans.Tags;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static io.github.incplusplus.thermostat.server.beans.Tags.getTags;
import static java.util.Collections.singletonList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@EnableSwagger2WebMvc
//@ComponentScan("io.github.incplusplus.thermostat")
@Configuration
public class SpringConfig
{
	@Bean
	public Docket petApi() {
		return
		conditionallyAddTags( new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//				.paths(PathSelectors.any())
				.build()
//				.pathMapping("/")
//				.directModelSubstitute(LocalDate.class, String.class)
//				.genericModelSubstitutes(ResponseEntity.class)
//				.alternateTypeRules(
//						newRule(typeResolver.resolve(DeferredResult.class,
//								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
//								typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET,
//						singletonList(new ResponseMessageBuilder()
//								.code(500)
//								.message("500 message")
//								.responseModel(new ModelRef("Error"))
//								.build())
//				)
				.securitySchemes(singletonList(apiKey()))
				.securityContexts(singletonList(securityContext()))
		);
	}
	
	@Autowired
	private Tag[] tags;
	
	/**
	 * This is to properly get around the weirdness with {@link Docket#tags(Tag, Tag...)}.
	 * The tags method is required to have at least one tag so this method runs under the
	 * condition that there is at least one tag and adds as many as are given by {@link io.github.incplusplus.thermostat.server.beans.Tags#getTags()}.
	 *
	 * @param docketToPossiblyAddTagsTo The {@link Docket} to operate on.
	 * @return The {@link Docket} that was input as an argument, possibly with tags added to it.
	 */
	private Docket conditionallyAddTags(Docket docketToPossiblyAddTagsTo) {
		Tag[] tagsArr = Tags.getTags();
		if (tagsArr.length < 1) {
			// There are no tags to add to this docket
			return docketToPossiblyAddTagsTo;
		} else if (tagsArr.length == 1) {
			// Add just the one tag
			return docketToPossiblyAddTagsTo.tags(tagsArr[0]);
		} else {
			// There is more than one tag. Add all of them.
			return docketToPossiblyAddTagsTo.tags(tagsArr[0], Arrays.copyOfRange(tagsArr, 1, tagsArr.length));
		}
	}
	
	private ApiKey apiKey() {
		return new ApiKey("mykey", "api_key", "header");
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/anyPath.*"))
				.build();
	}
	
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return singletonList(
				new SecurityReference("mykey", authorizationScopes));
	}
	
//	@Bean
//	SecurityConfiguration security() {
//		return SecurityConfigurationBuilder.builder()
//				.clientId("test-app-client-id")
//				.clientSecret("test-app-client-secret")
//				.realm("test-app-realm")
//				.appName("test-app")
//				.scopeSeparator(",")
//				.additionalQueryStringParams(null)
//				.useBasicAuthenticationWithAccessCodeGrant(false)
//				.enableCsrfSupport(false)
//				.build();
//	}
	
//	@Bean
//	UiConfiguration uiConfig() {
//		return UiConfigurationBuilder.builder()
//				.deepLinking(true)
//				.displayOperationId(false)
//				.defaultModelsExpandDepth(1)
//				.defaultModelExpandDepth(1)
//				.defaultModelRendering(ModelRendering.EXAMPLE)
//				.displayRequestDuration(false)
//				.docExpansion(DocExpansion.NONE)
//				.filter(false)
//				.maxDisplayedTags(null)
//				.operationsSorter(OperationsSorter.ALPHA)
//				.showExtensions(false)
//				.showCommonExtensions(false)
//				.tagsSorter(TagsSorter.ALPHA)
//				.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
//				.validatorUrl(null)
//				.build();
//	}
}
