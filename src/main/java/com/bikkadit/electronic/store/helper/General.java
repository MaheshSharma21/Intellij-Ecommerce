package com.bikkadit.electronic.store.helper;

import com.bikkadit.electronic.store.entities.User;
import com.bikkadit.electronic.store.payloads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class General {

    public static <E,U> PageableResponse<U> getPageableResponse(Page<E> page, Class<U> type ){

        //code reuseability
        //this class is for general purpose for every response of type like user,category,cart
        //E means entity type object and V means Dto type object
        //Class<U> means target type mtlb  pageableResponse object kis type ka hoga


        List<E> content = page.getContent();
        List<U> userdata = content.stream().map(data -> new ModelMapper().map(data, type)).collect(Collectors.toList());

        PageableResponse<U> response = new PageableResponse<U>();
        response.setContent(userdata);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;

    }
}
