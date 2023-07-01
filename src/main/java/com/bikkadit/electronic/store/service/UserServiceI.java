package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.UserDto;

import java.util.List;

public interface UserServiceI {

    UserDto saveUser(UserDto userDto);

    UserDto getUserById(String userId);

    PageableResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

    List<UserDto> searchUser(String keyword);

    UserDto getUserByEmail(String email);
}
