package com.bikkadit.electronic.store.payloads;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto extends BaseEntityDto {

    private String categoryId;

    @NotBlank
    @Size(min = 10, max = 25, message = "category title must be within min(10)and max(25) length")
    private String title;

    @NotBlank
    @Size(max = 40, message = "description length must be under 40 length")
    @NotNull
    private String description;

    @NotBlank(message = "cover Image is mandatory ....")
    private String coverImage;
}
