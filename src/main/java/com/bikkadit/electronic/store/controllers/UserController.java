package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.UserDto;
import com.bikkadit.electronic.store.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    /**
     * @author Mahesh Sharma
     * @apiNote this is the api related to save the User
     * @param userDto
     * @return
     */
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid  @RequestBody UserDto userDto) {
        log.info("Request Starting for service layer to save the user");
        UserDto userdto = this.userServiceI.saveUser(userDto);
        log.info("Request completed for service layer to save the user");
        return new ResponseEntity<>(userdto, HttpStatus.CREATED);
    }


    /**
     * @author Mahesh Sharma
     * @apiNote This api is used to get the user by userId
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Request Starting for service layer to get the user by userId : {}", userId);
        UserDto userdto = this.userServiceI.getUserById(userId);
        log.info("Request completed for service layer to get the user by userId : {}", userId);
        return new ResponseEntity<>(userdto, HttpStatus.FOUND);

    }

    /**
     *  @author Mahesh Sharma
     *  @apiNote This is the api to get All users
     * @return
     */
    @GetMapping()
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value ="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value="pageSize",defaultValue = "2",required = false) Integer pageSize,
            @RequestParam(value ="sortBy",defaultValue = "email",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "desc",required = false) String sortDir
    ) {
        log.info("Request Starting for service layer to get All users");
        PageableResponse<UserDto> allUsers = this.userServiceI.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to get All users");
        return new ResponseEntity<>(allUsers, HttpStatus.OK);

    }

    /**
     * @author Mahesh Sharma
     * @apiNote This is the api to update the user by userId
     * @param userDto
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser( @Valid  @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Request Starting for service layer to update the user by userId : {}", userId);
        UserDto userdto = this.userServiceI.updateUser(userDto, userId);
        log.info("Request completed for service layer to update the user by userId : {}", userId);
        return new ResponseEntity<>(userdto, HttpStatus.OK);

    }

    /**
     *  @author Mahesh Sharma
     *  @apiNote This api is used to delete the user by userId
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        log.info("Request Starting for service layer to delete the user by userId : {}", userId);
        this.userServiceI.deleteUser(userId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(AppConstant.USER_DELETE)
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        log.info("Request completed for service layer to delete the user by userId : {}", userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }


    /**
     * @author Mahesh Sharma
     * @apiNote Note This api is used to get user by  user email
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getuserbyEmail(@PathVariable String email) {
        log.info("Request Starting for service layer to get the user by email ;{}", email);
        UserDto userdto = this.userServiceI.getuserbyEmail(email);
        log.info("Request completed for service layer to get the user by email ;{}", email);
        return new ResponseEntity<>(userdto, HttpStatus.OK);

    }

    /**
     * @author Mahesh Sharma
     *  @apiNote this api is used to search User by some keyword
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Request Starting for service layer to search the user with keyword :{}", keyword);
        List<UserDto> userdto = this.userServiceI.searchUser(keyword);
        log.info("Request completed for service layer to search the user with keyword :{}", keyword);
        return new ResponseEntity<>(userdto, HttpStatus.FOUND);

    }


}
