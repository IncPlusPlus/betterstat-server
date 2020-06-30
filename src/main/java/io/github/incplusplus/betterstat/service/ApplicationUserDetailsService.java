package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.User;
import io.github.incplusplus.betterstat.persistence.repositories.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public ApplicationUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (user == null) {
      // TODO: Change this to something more suitable
      throw new RuntimeException();
    }
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), Collections.emptyList());
  }
}
