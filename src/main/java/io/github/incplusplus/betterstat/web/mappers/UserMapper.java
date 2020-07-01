package io.github.incplusplus.betterstat.web.mappers;

import io.github.incplusplus.betterstat.persistence.model.User;
import io.github.incplusplus.betterstat.web.dto.UserDto;
import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

  UserDto toDTO(User user);

  User fromDto(UserDto userDto);

  List<UserDto> mapUsersToUserDto(List<User> users);
}
