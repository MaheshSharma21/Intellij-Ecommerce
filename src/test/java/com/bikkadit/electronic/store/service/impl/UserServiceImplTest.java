package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.User;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.UserDto;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepo;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private ModelMapper mapper;

    User user;

    @BeforeEach
    void init() {
        user = User.builder()
                .about("testing for userCreate method")
                .name("Mahesh")
                .email("maheshsharma@gmail.com")
                .gender("male")
                .password("ms@123")
                .State("punjab")
                .imageName("xyz.png").build();

    }

    @Test
    void saveUser() {

        //stubbing
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        //actual method calling
        UserDto userDto = userService.saveUser(this.mapper.map(user, UserDto.class));
        //matching using assertion
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("Mahesh", userDto.getName(), "UserName not matched ");

    }

    @Test
    void getUserById() {

        String userId = "xyz";
        //stubbing
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        //actual method calling
        UserDto userdto = userService.getUserById(userId);
        //matching using assertion
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getGender(), userdto.getGender(), "Gender not matched ");
    }

    @Test
    void getAllUsers() {
        User user1 = User.builder()
                .about("testing for getAllUser method")
                .name("rohan")
                .email("rohan@gmail.com")
                .gender("male")
                .password("rs@123")
                .State("delhi")
                .imageName("abc.png").build();

        User user2 = User.builder()
                .about("testing for getAllUser method")
                .name("sohan")
                .email("sohan@gmail.com")
                .gender("male")
                .password("sh@123")
                .State("rajasthan")
                .imageName("klm.png").build();

        List<User> list = Arrays.asList(user, user1, user2);

        Page<User> page = new PageImpl<>(list);

        //stubbing
        Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        //actual method calling
        PageableResponse<UserDto> allUsers = userService.getAllUsers(1, 2, "state", "desc");
        //matching using assertion
        Assertions.assertEquals(3, allUsers.getContent().size(), "Size not validate");

    }

    @Test
    void updateUser() {
        String userId = "abc";
        UserDto userDto = UserDto.builder()
                .about(" updated testing for updateProduct method")
                .name("pankaj")
                .email("maheshsharma@gmail.com")
                .gender("male")
                .password("pk@123")
                .imageName("pks.png").build();

        //stubbing
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        //actual method calling
        UserDto userDto1 = userService.updateUser(userDto, userId);
        //matching using assertion
        Assertions.assertEquals("pankaj", userDto1.getName(), "username not validate");
        Assertions.assertNotNull(userDto1);

    }

    @Test
    void deleteUser() {

        String userId = "xyz";
        //stubbing
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        //actual method calling
        userService.deleteUser(userId);
        //verifying result
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }

    @Test
    void getUserByEmail() {

        String email = "maheshsharma@gmail.com";
        //stubbing
        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        //actual method calling
        UserDto userdto = userService.getUserByEmail(email);
        //matching using assertion
        Assertions.assertEquals(user.getEmail(), userdto.getEmail(), "Email not matched ");
    }

    @Test
    void searchUser() {

        User user1 = User.builder()
                .about("testing for search user method")
                .name("pankaj")
                .email("pankaj@gmail.com")
                .gender("male")
                .password("pj@123")
                .State("odisha")
                .imageName("pankaj.png").build();

        User user2 = User.builder()
                .about("testing for search user method")
                .name("jason")
                .email("jason@gmail.com")
                .gender("male")
                .password("rk@123")
                .State("goa")
                .imageName("jason.png").build();


        String keyword = "jason";
        //stubbing
        Mockito.when(userRepo.findByNameContaining(keyword)).thenReturn(Arrays.asList(user, user1, user2));
        //actual method calling
        List<UserDto> userDto = userService.searchUser(keyword);
        //matching using assertion
        Assertions.assertEquals(3, userDto.size(), "size not matched");
    }
}

