package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.ThermostatApiUser;
import io.github.incplusplus.betterstat.persistence.repositories.ThermostatApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ThermostatApiUserUserDetailsService implements UserDetailsService {

  private final ThermostatApiUserRepository thermostatApiUserRepository;

  @Autowired
  public ThermostatApiUserUserDetailsService(
      ThermostatApiUserRepository thermostatApiUserRepository) {
    this.thermostatApiUserRepository = thermostatApiUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ThermostatApiUser thermostatApiUser = thermostatApiUserRepository.findByUsername(username);
    if (thermostatApiUser == null) {
      // TODO: Change this to something more suitable
      throw new RuntimeException();
    }
    return new User(
        thermostatApiUser.getUsername(),
        thermostatApiUser.getPassword(),
        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEVICE"));
  }
}
