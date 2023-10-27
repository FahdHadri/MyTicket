package tn.dkSoft.MyTicket.service;

import tn.dkSoft.MyTicket.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDto) throws Exception;

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);


    UserDto updateUser(Long userId, UserDto userDto);


   void deleteUser(Long userId);
}
