package sena.activitytracker.acktrack.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import sena.activitytracker.acktrack.dtos.UserDTO;
import sena.activitytracker.acktrack.model.security.User;

@Mapper
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    UserDTO toUserDTO(User user);
    User toUser(UserDTO user);
}
