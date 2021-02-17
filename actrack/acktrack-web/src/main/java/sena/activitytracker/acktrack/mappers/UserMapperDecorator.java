package sena.activitytracker.acktrack.mappers;


import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sena.activitytracker.acktrack.dtos.UserDTO;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.model.security.User;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class UserMapperDecorator implements UserMapper{

    private UserMapper userMapper;

    @Autowired
    @Qualifier("delegate")
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    /*todo: Override generated methods */
    @Override
    public UserDTO toUserDTO(User user){

        UserDTO userDTO = userMapper.toUserDTO(user);
        /* roles */
        userDTO.setRolesSet(getRoles(user));

        return userDTO;
    }

    private Set<String> getRoles(User user) {

        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
