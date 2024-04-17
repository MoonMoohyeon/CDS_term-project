package cds.team20.whiteboard.service;

import cds.team20.whiteboard.dto.UserDto;
import cds.team20.whiteboard.entity.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);
    Iterable<UserEntity> getUserByAll();
}
