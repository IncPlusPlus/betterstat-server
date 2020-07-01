package io.github.incplusplus.betterstat.testhelpers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockUser {
  String emailAddress() default "rob@mail.com";

  String firstName() default "Rob";

  String lastName() default "Winch";
}
