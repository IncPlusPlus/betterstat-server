package io.github.incplusplus.betterstat.web.mappers;

import io.github.incplusplus.betterstat.persistence.model.User;
import io.github.incplusplus.betterstat.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserMapperDecorator implements UserMapper {

  @Autowired
  @Qualifier("delegate")
  private UserMapper delegate;

  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public User fromDto(UserDto userDto) {
    User user = delegate.fromDto(userDto);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    return user;
  }
}
