package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.ImageResponse;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.UserDto;
import com.bikkadit.electronic.store.service.FileServiceI;
import com.bikkadit.electronic.store.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private FileServiceI fileServiceI;

    @Value("${user.profile.image.path}")
    private String uploadImagepath;

    /**
     * @param userDto
     * @return
     * @author Mahesh Sharma
     * @apiNote this is the api related to save the User
     */
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("Request Starting for service layer to save the user");
        UserDto userdto = this.userServiceI.saveUser(userDto);
        log.info("Request completed for service layer to save the user");
        return new ResponseEntity<>(userdto, HttpStatus.CREATED);
    }


    /**
     * @param userId
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to get the user by userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Request Starting for service layer to get the user by userId : {}", userId);
        UserDto userdto = this.userServiceI.getUserById(userId);
        log.info("Request completed for service layer to get the user by userId : {}", userId);
        return new ResponseEntity<>(userdto, HttpStatus.FOUND);

    }

    /**
     * @return
     * @author Mahesh Sharma
     * @apiNote This is the api to get All users
     */
    @GetMapping()
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) Integer pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) Integer pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE, required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {
        log.info("Request Starting for service layer to get All users");
        PageableResponse<UserDto> allUsers = this.userServiceI.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to get All users");
        return new ResponseEntity<>(allUsers, HttpStatus.OK);

    }

    /**
     * @param userDto
     * @param userId
     * @return
     * @author Mahesh Sharma
     * @apiNote This is the api to update the user by userId
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Request Starting for service layer to update the user by userId : {}", userId);
        UserDto userdto = this.userServiceI.updateUser(userDto, userId);
        log.info("Request completed for service layer to update the user by userId : {}", userId);
        return new ResponseEntity<>(userdto, HttpStatus.OK);

    }

    /**
     * @param userId
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to delete the user by userId
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
     * @param email
     * @return
     * @author Mahesh Sharma
     * @apiNote Note This api is used to get user by  user email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getuserbyEmail(@PathVariable String email) {
        log.info("Request Starting for service layer to get the user by email ;{}", email);
        UserDto userdto = this.userServiceI.getuserbyEmail(email);
        log.info("Request completed for service layer to get the user by email ;{}", email);
        return new ResponseEntity<>(userdto, HttpStatus.OK);

    }

    /**
     * @param keyword
     * @return
     * @author Mahesh Sharma
     * @apiNote this api is used to search User by some keyword
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Request Starting for service layer to search the user with keyword :{}", keyword);
        List<UserDto> userdto = this.userServiceI.searchUser(keyword);
        log.info("Request completed for service layer to search the user with keyword :{}", keyword);
        return new ResponseEntity<>(userdto, HttpStatus.FOUND);

    }

    /**
     * @param file
     * @param userId
     * @return
     * @throws IOException
     * @author Mahesh Sharma
     * @apiNote This api is used to upload Image
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("userImage") MultipartFile file, @PathVariable String userId) throws IOException {
        log.info(" Request Starting for fileservice layer to upload image with userId :{}", userId);
        String ImageName = fileServiceI.uploadImage(file, uploadImagepath);

        UserDto user = userServiceI.getUserById(userId);

        user.setImageName(ImageName);
        UserDto userDto = userServiceI.updateUser(user, userId);
        ImageResponse fileUploaded = ImageResponse.builder().imageName(ImageName).message("File uploaded ").success(true).Status(HttpStatus.CREATED).build();
        log.info(" Request completed for fileservice layer to upload image with userId :{}", userId);
        return new ResponseEntity<>(fileUploaded, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @param response
     * @throws IOException
     * @author Mahesh Sharma
     * @apiNote This api is used to server Image
     */
    @GetMapping("/image/{userId}")
    public void serverImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info(" Request Starting for fileservice layer to serve image with userId :{}", userId);
        UserDto user = userServiceI.getUserById(userId);
        log.info(" user Image Name :{}", user.getImageName());
        InputStream resource = fileServiceI.getResource(uploadImagepath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info(" Request completed for fileservice layer to serve image with userId :{}", userId);
    }
}
