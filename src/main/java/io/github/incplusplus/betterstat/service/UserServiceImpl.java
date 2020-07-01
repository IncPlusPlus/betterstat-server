package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.User;
import io.github.incplusplus.betterstat.persistence.repositories.UserRepository;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User registerNewUserAccount(User user) {
    if (emailExists(user.getEmail())) {
      throw new RuntimeException("There is an account with that email address: " + user.getEmail());
    }
    return userRepository.save(user);
  }

  @Override
  public User updateUser(String id, User user) throws ObjectNotFoundException {
    if (!userRepository.existsById(id)) {
      throw new ObjectNotFoundException(id, User.class);
    }
    // If it already exists, set the ID of the DTO to the ID we know exists in our repository.
    user.setId(id);
    // Saving an object with an ID that already exists will simply update it.
    // https://stackoverflow.com/a/56207430/1687436
    return userRepository.save(user);
  }

  @Override
  public User deleteUser(User user) throws ObjectNotFoundException {
    Optional<User> userOptional = userRepository.findById(user.getId());
    if (userOptional.isEmpty()) {
      throw new ObjectNotFoundException(user.getId(), User.class);
    }
    userRepository.deleteById(user.getId());
    return userOptional.get();
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> getUserByID(String id) {
    return userRepository.findById(id);
  }

  @Override
  public void changeUserPassword(User user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
  }

  @Override
  public boolean checkIfValidOldPassword(User user, String password) {
    return passwordEncoder.matches(password, user.getPassword());
  }

  private boolean emailExists(final String email) {
    return userRepository.findByEmail(email) != null;
  }
}
