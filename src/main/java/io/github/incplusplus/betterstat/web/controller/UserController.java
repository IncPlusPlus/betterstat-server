package io.github.incplusplus.betterstat.web.controller;

import io.github.incplusplus.betterstat.persistence.model.Schedule;
import io.github.incplusplus.betterstat.persistence.model.User;
import io.github.incplusplus.betterstat.service.UserService;
import io.github.incplusplus.betterstat.web.dto.UserDto;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import io.github.incplusplus.betterstat.web.mappers.UserMapper;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @Autowired
  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  //  @GetMapping
  //  public List<UserDto> getAllSchedules() {
  //    return userMapper.mapUsersToUserDto(userService.findAll());
  //  }
  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable String id) throws ObjectNotFoundException {
    Optional<User> userOptional = userService.getUserByID(id);
    if (userOptional.isEmpty()) {
      throw new ObjectNotFoundException(id, Schedule.class);
    }
    return userMapper.toDTO(userOptional.get());
  }

  @PostMapping
  public UserDto createUser(@RequestBody UserDto userDto) {
    return userMapper.toDTO(userService.registerNewUserAccount(userMapper.fromDto(userDto)));
  }

  @PostConstruct
  void addAUser() {
    UserDto userDto = new UserDto();
    userDto.setFirstName("Ryan");
    userDto.setLastName("Cloherty");
    userDto.setEmail("cloherty.ryan@gmail.com");
    userDto.setPassword("mypassword");
    try {
      userService.registerNewUserAccount(userMapper.fromDto(userDto));
    } catch (RuntimeException e) {
      System.out.println("User already registered");
    }
  }
}
