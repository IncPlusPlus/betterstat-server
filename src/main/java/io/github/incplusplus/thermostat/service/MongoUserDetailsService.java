package io.github.incplusplus.thermostat.service;

import io.github.incplusplus.thermostat.persistence.model.ApiUser;
import io.github.incplusplus.thermostat.persistence.repositories.ApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MongoUserDetailsService implements UserDetailsService
{
	@Autowired
	private ApiUserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		ApiUser apiUser = repository.findByUsername(username);
		if (apiUser == null)
		{
			throw new UsernameNotFoundException("User not found");
		}
		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
		return new org.springframework.security.core.userdetails.User(apiUser.getUsername(), apiUser.getPassword(), authorities);
	}
}
