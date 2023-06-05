package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.User;

import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.payloads.UserDto;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserServiceI {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        log.info("Request Starting  to save the user");

        User user = this.mapper.map(userDto, User.class);

        //for every time random Id will be stored
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);
        user.setCreatedBy(userDto.getCreatedBy());
        user.setUpdatedBy(userDto.getUpdatedBy());


        User usersaved = this.userRepo.save(user);
        log.info("Request completed  to save the user");
        return this.mapper.map(usersaved, UserDto.class);
    }


    @Override
    public UserDto getUserById(String userId) {
        log.info("Request Starting  to get the user by userId : {}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));
        log.info("Request completed to  get the user by userId : {}", userId);
        return this.mapper.map(user, UserDto.class);

    }

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Request Starting  to get All users");
        List<User> list = this.userRepo.findAll();
        List<UserDto> userdata = list.stream().map(data -> this.mapper.map(data, UserDto.class)).collect(Collectors.toList());
        log.info("Request completed  to get All users");
        return userdata;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Request Starting  to update the user by userId : {}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));

        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User user1 = this.userRepo.save(user);
        log.info("Request completed  to update the user by userId : {}", userId);
        return this.mapper.map(user1, UserDto.class);

    }

    @Override
    public void deleteUser(String userId) {
        log.info("Request Starting  to delete the user by userId : {}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));
        this.userRepo.delete(user);
        log.info("Request completed  to delete the user by userId : {}", userId);

    }


    @Override
    public UserDto getuserbyEmail(String email) {
        log.info("Request Starting  to get the user by email ;{}", email);
        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_EMAIL));
        log.info("Request completed  to get the user by email ;{}", email);
        return this.mapper.map(user, UserDto.class);
    }


    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Request Starting  to search the user with keyword :{}", keyword);
        List<User> list = this.userRepo.findByNameContaining(keyword);
        log.info("Request completed to search the user with keyword :{}", keyword);
        return list.stream().map(data -> this.mapper.map(data, UserDto.class)).collect(Collectors.toList());
    }


}
