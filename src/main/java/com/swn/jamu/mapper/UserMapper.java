package com.swn.jamu.mapper;

import com.swn.jamu.dto.UserDTO;
import com.swn.jamu.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "branch", ignore = true)
    User toUser(UserDTO userDTO);

    @Mapping(target = "role", expression = "java(getRole(user))")
    UserDTO toUserDTO(User user);

    default String getRole(User user) {
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            return user.getRoles().get(0).getName();
        } else {
            return null;
        }
    }
}
