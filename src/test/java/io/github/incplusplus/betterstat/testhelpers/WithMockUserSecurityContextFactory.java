package io.github.incplusplus.betterstat.testhelpers;

import io.github.incplusplus.betterstat.persistence.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockUser> {
  @Override
  public SecurityContext createSecurityContext(WithMockUser customUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    User principal = new User();
    principal.setFirstName(customUser.firstName());
    principal.setLastName(customUser.lastName());
    principal.setEmail(customUser.emailAddress());
    Authentication auth =
        new UsernamePasswordAuthenticationToken(
            principal,
            "password",
            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEVICE"));
    context.setAuthentication(auth);
    return context;
  }
}
