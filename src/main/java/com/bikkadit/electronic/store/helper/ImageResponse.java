package com.bikkadit.electronic.store.helper;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {

    String imageName;
    String message;
    boolean success;
    HttpStatus Status;

}
