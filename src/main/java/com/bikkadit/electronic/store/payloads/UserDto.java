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

    @Email(message = " Email Id not according to Standards ......")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-z5-9]{6}")
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
