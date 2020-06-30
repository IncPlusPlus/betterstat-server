package io.github.incplusplus.betterstat.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Configuration
  @Order(1)
  public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("applicationUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.httpBasic()
          .and()
          .csrf()
          .disable()
          .requestMatchers(
              requestMatcherConfigurer ->
                  requestMatcherConfigurer.requestMatchers(
                      new NegatedRequestMatcher(new AntPathRequestMatcher("/thermostat-api/**"))))
          .authorizeRequests()
          .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/oauth/**")
          .permitAll()
          .anyRequest()
          .authenticated();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
  }

  @Configuration
  @Order(2)
  public static class ApiTokenSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("thermostatApiUserUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.httpBasic()
          .and()
          .csrf()
          .disable()
          .requestMatcher(new AntPathRequestMatcher("/thermostat-api/**"))
          .authorizeRequests()
          .anyRequest()
          .authenticated();
      /* other config options go here... */
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService);
    }
  }
}
