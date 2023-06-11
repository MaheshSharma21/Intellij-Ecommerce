package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseEntityDto {

    private String userId;

    @Size(min = 4, max = 15, message = " Please Enter valid User name !!!")
    @NotBlank
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = " Please enter valid Email format ")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[^\\s]{8,20}$", message = "Password must be at least 8 characters, not greater than 20 characters ,contains atleast one ")
    private String password;

    @NotBlank(message = "Please Specify  valid gender .....")
    @Size(min = 4, max = 6, message = " min length for gender is 4 and max length is 6")
    private String gender;

    @Size(min = 10, max = 25, message = "please tell  something about User ....in 10 to 25  characters ")
    @NotEmpty
    private String about;

    @ImageNameValid(message = "Image name not valid ..")
    private String imageName;

}


//email pattern validation
//@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
//^ asserts the start of the string.
//[a-zA-Z0-9._%+-]+ matches one or more alphanumeric characters, dots, underscores, percentage signs, plus signs, or hyphens, which are valid characters for the username part of the email address.
//@ matches the "@" symbol.
//[a-zA-Z0-9.-]+ matches one or more alphanumeric characters, dots, or hyphens, which are valid characters for the domain part of the email address.
// \. matches the dot (.) symbol.
//[a-zA-Z]{2,} matches two or more consecutive alphabetical characters, representing the top-level domain (TLD) such as .com, .org, .net, etc.
//$ asserts the end of the string.


//password pattern
//@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[^\\s]{8,20}$")
//^ asserts the start of the string.
//        (?=.*[a-z]) is a positive lookahead to ensure the presence of at least one lowercase letter.
//        (?=.*[A-Z]) is a positive lookahead to ensure the presence of at least one uppercase letter.
//        (?=.*\d) is a positive lookahead to ensure the presence of at least one digit.
//        [^\s] matches any character that is not a whitespace character.
//        {8,20} specifies that the previous character class should occur between 8 and 20 times.
//        $ asserts the end of the string.