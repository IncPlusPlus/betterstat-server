package io.github.incplusplus.betterstat.server.config;

import io.github.incplusplus.betterstat.Application;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// @EnableSwagger2WebMvc
@ComponentScan(basePackageClasses = Application.class)
@Configuration
public class SpringConfig {

  @Bean
  public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
    return new OpenAPI()
        .info(
            new Info()
                .title("Betterstat Server API")
                .version(appVersion)
                .description(
                    "This is the server that powers the betterstat project. It interacts with the mobile app and the thermostats. You can find code and documentation for it [here](https://github.com/IncPlusPlus/betterstat-server)")
                .license(
                    new License()
                        .name("MIT License")
                        .url(
                            "https://github.com/IncPlusPlus/betterstat-server/blob/master/LICENSE")))
        .components(
            new Components()
                .addSecuritySchemes(
                    "basicScheme",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
  }
  //	@Bean
  //	publicW Docket mainApi() {
  //		return
  //		conditionallyAddTags( new Docket(DocumentationType.SWAGGER_2)
  //				.select()
  //				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
  //				.paths(PathSelectors.any())
  //				.build()
  ////				.pathMapping("/")
  ////				.directModelSubstitute(LocalDate.class, String.class)
  ////				.genericModelSubstitutes(ResponseEntity.class)
  ////				.alternateTypeRules(
  ////						newRule(typeResolver.resolve(DeferredResult.class,
  ////								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
  ////								typeResolver.resolve(WildcardType.class)))
  //				.useDefaultResponseMessages(false)
  ////				.globalResponseMessage(RequestMethod.GET,
  ////						singletonList(new ResponseMessageBuilder()
  ////								.code(500)
  ////								.message("500 message")
  ////								.responseModel(new ModelRef("Error"))
  ////								.build())
  ////				)
  //				.securitySchemes(singletonList(basicAuth()))
  //				.securityContexts(singletonList(securityContext()))
  //		);
  //	}
  //
  //	/**
  //	 * This is to properly get around the weirdness with {@link Docket#tags(Tag, Tag...)}.
  //	 * The tags method is required to have at least one tag so this method runs under the
  //	 * condition that there is at least one tag and adds as many as are given by {@link
  // io.github.incplusplus.thermostat.server.beans.Tags#getTags()}.
  //	 *
  //	 * @param docketToPossiblyAddTagsTo The {@link Docket} to operate on.
  //	 * @return The {@link Docket} that was input as an argument, possibly with tags added to it.
  //	 */
  //	private Docket conditionallyAddTags(Docket docketToPossiblyAddTagsTo) {
  //		Tag[] tagsArr = Tags.getTags();
  //		if (tagsArr.length < 1) {
  //			// There are no tags to add to this docket
  //			return docketToPossiblyAddTagsTo;
  //		} else if (tagsArr.length == 1) {
  //			// Add just the one tag
  //			return docketToPossiblyAddTagsTo.tags(tagsArr[0]);
  //		} else {
  //			// There is more than one tag. Add all of them.
  //			return docketToPossiblyAddTagsTo.tags(tagsArr[0], Arrays.copyOfRange(tagsArr, 1,
  // tagsArr.length));
  //		}
  //	}
  //
  //	private BasicAuth basicAuth() {
  //		return new BasicAuth("basicAuth");
  //	}
  //
  //	private SecurityContext securityContext() {
  //		return SecurityContext.builder()
  //				.securityReferences(defaultAuth())
  //				.forPaths(PathSelectors.any())
  //				.build();
  //	}
  //
  //	List<SecurityReference> defaultAuth() {
  //		AuthorizationScope authorizationScope
  //				= new AuthorizationScope("global", "accessEverything");
  //		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
  //		authorizationScopes[0] = authorizationScope;
  //		return singletonList(
  //				new SecurityReference("mykey", authorizationScopes));
  //	}

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
