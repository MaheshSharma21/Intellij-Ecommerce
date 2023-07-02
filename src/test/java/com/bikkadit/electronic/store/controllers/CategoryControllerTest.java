package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.service.CategoryServiceI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

//    @MockBean
//    private CategoryServiceI categoryService;
//
//
//    @Autowired
//    private ModelMapper mapper;
////
////    @Autowired
//    private MockMvc mockmvc;
//
//    Category category;
//
//    void init (){
//        category = Category.builder()
//                .title(" this is a sd card related category")
//                .description("SD card available for every smart phones on minimum prize")
//                .coverImage("abc.png").build();
//
//    }
//    @Test
//    void createCategory() {
//
//        CategoryDto categoryDto = this.mapper.map(category, CategoryDto.class);
//
//        //Stubbing
//        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);
//
//        //request create for url
//        this.mockmvc.perform(
//                MockMvcRequestBuilders.post("/api/category")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(convertObjectToJsonString(category))
//                        .content())
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.title").exists());
//
//    }
//
//    private String convertObjectToJsonString(Object category) {
//
//        try {
//            return new ObjectMapper().writeValueAsString(category);
//        } catch (Exception e) {
//          e.printStackTrace();
//
//        }
//    }


    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }

    @Test
    void getCategoryById() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void searchCategoryByTitle() {
    }

    @Test
    void uploadCoverImage() {
    }

    @Test
    void serverImage() {
    }
}