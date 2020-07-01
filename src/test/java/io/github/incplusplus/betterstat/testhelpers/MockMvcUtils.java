package io.github.incplusplus.betterstat.testhelpers;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.config;
import static io.restassured.module.mockmvc.config.MockMvcConfig.mockMvcConfig;

import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;

public class MockMvcUtils {

  /**
   * THIS SHOULD ONLY BE USED FOR UNIT TESTS! Spring Security <i>absolutely</i> be active and tested
   * by the integration tests!!! See <a
   * href="https://github.com/spring-projects/spring-security/issues/7745#issuecomment-567239205">spring-projects/spring-security#7745</a>
   *
   * @return a RestAssuredMockMvcConfig that prevents Spring Security from throwing a tantrum
   */
  public static RestAssuredMockMvcConfig noSecurity() {
    return config()
        .mockMvcConfig(mockMvcConfig().dontAutomaticallyApplySpringSecurityMockMvcConfigurer());
  }
}
