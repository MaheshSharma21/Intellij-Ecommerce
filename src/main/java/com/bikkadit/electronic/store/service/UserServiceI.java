package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.payloads.UserDto;

import java.util.List;

public interface UserServiceI {

    UserDto saveUser(UserDto userDto);


    UserDto getUserById(String userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

    List<UserDto> searchUser(String keyword);

    UserDto getuserbyEmail(String email);
}
