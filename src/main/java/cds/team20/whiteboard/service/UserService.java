package cds.team20.whiteboard.service;

public interface UserService {
    UserDto createUser(UserDto userDto);
    Iterable<UserEntity> getUserByAll();
}
