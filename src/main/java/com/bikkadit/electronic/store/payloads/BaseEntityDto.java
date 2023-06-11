package com.bikkadit.electronic.store.payloads;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityDto implements Serializable {

    private LocalDate updatedDate;

    @Size(min = 4, max = 20, message = " Please provide valid  Name !!!")
    @NotEmpty
    private String updatedBy;

    private Boolean isActive = true;

    private LocalDate createdDate;

    @Size(min = 4, max = 20, message = " Please Enter valid  Name !!!")
    @NotBlank
    private String createdBy;

}
