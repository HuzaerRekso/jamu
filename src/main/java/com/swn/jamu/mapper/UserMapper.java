package com.swn.jamu.mapper;

import com.swn.jamu.dto.UserDTO;
import com.swn.jamu.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserDTO userDTO);

    UserDTO toUserDTO(User user);
}
