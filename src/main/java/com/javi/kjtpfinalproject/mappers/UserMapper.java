package com.javi.kjtpfinalproject.mappers;

import com.javi.kjtpfinalproject.dto.UserDTO;
import com.javi.kjtpfinalproject.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO userToUserDto(User user);
}
