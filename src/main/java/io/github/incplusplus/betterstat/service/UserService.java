package io.github.incplusplus.betterstat.service;

import io.github.incplusplus.betterstat.persistence.model.User;
import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.Optional;

public interface UserService {

  User registerNewUserAccount(User accountDto);

  User updateUser(String id, User user) throws ObjectNotFoundException;

  User deleteUser(User user) throws ObjectNotFoundException;

  User findUserByEmail(String email);

  Optional<User> getUserByID(String id);

  void changeUserPassword(User user, String password);

  boolean checkIfValidOldPassword(User user, String password);
}
