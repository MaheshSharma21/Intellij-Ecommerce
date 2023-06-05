package com.bikkadit.electronic.store.payloads;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityDto implements Serializable {

    private LocalDate updatedDate;
    private String updatedBy;
    private Boolean isActive=true;
    private LocalDate createdDate;
    private String createdBy;

}
