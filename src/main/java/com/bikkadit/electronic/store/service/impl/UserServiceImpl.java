package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.User;

import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.General;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.UserDto;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${user.profile.image.path}")
    private String Imagepath;


    @Override
    public UserDto saveUser(UserDto userDto) {
        log.info("Request Starting  to save the user");

        User user = this.mapper.map(userDto, User.class);

        //for every time random Id will be stored
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);


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
    public PageableResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Request Starting  to get All users");

        // Sort sort = Sort.by(sortBy);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable of = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = this.userRepo.findAll(of);

        PageableResponse<UserDto> res = General.getPageableResponse(page, UserDto.class);

        log.info("Request completed  to get All users");
        return res;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Request Starting  to update the user by userId : {}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));

        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        user.setCreatedBy(userDto.getCreatedBy());
        user.setUpdatedBy(userDto.getUpdatedBy());

        User user1 = this.userRepo.save(user);
        log.info("Request completed  to update the user by userId : {}", userId);
        return this.mapper.map(user1, UserDto.class);

    }

    @Override
    public void deleteUser(String userId) {
        log.info("Request Starting  to delete the user by userId : {}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));

        //delete user profile image

        //full path
        String fullpath = Imagepath + user.getImageName();

        try {
            Path path = Paths.get(fullpath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.error("User Image not found in folder :{}", ex.getMessage());
        } catch (IOException ex) {
            log.error("unable to found  user Image :{}", ex.getMessage());

        }

        //delete user
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
