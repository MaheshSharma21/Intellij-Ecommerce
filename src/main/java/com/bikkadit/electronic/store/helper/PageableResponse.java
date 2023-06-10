package com.bikkadit.electronic.store.helper;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PageableResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;

}
