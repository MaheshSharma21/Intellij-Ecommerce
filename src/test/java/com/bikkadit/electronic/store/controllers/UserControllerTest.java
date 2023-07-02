package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.entities.User;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.UserDto;
import com.bikkadit.electronic.store.service.UserServiceI;
import com.bikkadit.electronic.store.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.configuration.IMockitoConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    //controller testing by using mockmvc framework

    @MockBean
    private UserServiceImpl userServiceI;

    @Autowired
    private ModelMapper mapper ;

    @Autowired
    private MockMvc mockMvc;

    User user;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .about("testing for controller")
                .name("Mahesh")
                .email("maheshsharma@gmail.com")
                .gender("male")
                .password("abcdeFDG65")
                .State("punjab")
                .imageName("xyz.png")
                .build();

    }

    @Test
    void saveUser() throws Exception {
        UserDto userdto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.saveUser(Mockito.any())).thenReturn(userdto);

        //request for url
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());



    }

    //extra method for conversion
    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Test
    void getUserById() throws Exception {
        String userId ="avc";
        UserDto userDto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.getUserById(Mockito.anyString())).thenReturn(userDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    void getAllUsers() throws Exception {
        UserDto user1 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("vikas")
                .email("mohitsharma@gmail.com")
                .gender("male")
                .password("ms@123")
                .imageName("xyz.png").build();

       UserDto user2 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("sohan")
                .email("sohan@gmail.com")
                .gender("male")
                .password("ms@123")
                .imageName("abc.png").build();

        UserDto user3 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("sohan")
                .email("sohan@gmail.com")
                .gender("male")
                .password("ms@123")
                .imageName("abc.png").build();

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(user1,user2,user3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(userServiceI.getAllUsers(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void updateUser() throws Exception {

        String userId ="abc";

        UserDto userdto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userdto);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/user/"+userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }


    @Test
    void deleteUser() {

//        String userid ="avc";
//        UserDto userDto = this.mapper.map(user, UserDto.class);
//        Mockito.when(userServiceI.deleteUser(Mockito.anyString())).th
//
//        this.mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/user"+userid)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
   }

    @Test
    void getUserByEmail() throws Exception {
        String emailId ="maheshsharma@gmail.com";
        UserDto userDto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.getUserByEmail(Mockito.anyString())).thenReturn(userDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/email/"+emailId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }





    @Test
    void searchUser() throws Exception {

        String keyword ="image";
        UserDto user1 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("vikas")
                .email("mohitsharma@gmail.com")
                .gender("male")
                .password("ms@123")
                .imageName("xyz.png").build();

        UserDto user2 = UserDto.builder()
                .about("testing for getAll controller method")
                .name("sohan")
                .email("sohan@gmail.com")
                .gender("male")
                .password("ms@123")
                .imageName("abc.png").build();

        Mockito.when(userServiceI.searchUser(Mockito.anyString())).thenReturn(Arrays.asList(user1,user2));

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/search/"+keyword)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());


    }

    @Test
    void uploadImage() {
    }

    @Test
    void serverImage() {
    }
}