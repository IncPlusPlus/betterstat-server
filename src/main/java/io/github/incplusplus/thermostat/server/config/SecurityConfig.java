//package io.github.incplusplus.thermostat.server.config;
//
//import io.github.incplusplus.thermostat.security.google2fa.CustomAuthenticationProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//@Configuration
//@EnableConfigurationProperties
//public class SecurityConfig extends WebSecurityConfigurerAdapter
//{
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.csrf().disable()
//				.authorizeRequests()
//					.antMatchers("/v2/**","/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
//					.anyRequest().authenticated()
//				.and()
//				.httpBasic()
//				.and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
//
//	@Bean
//	public PasswordEncoder encoder() {
//		return new BCryptPasswordEncoder(11);
//	}
//
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authProvider());
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//		final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService);
//		authProvider.setPasswordEncoder(encoder());
//		return authProvider;
//	}
//}