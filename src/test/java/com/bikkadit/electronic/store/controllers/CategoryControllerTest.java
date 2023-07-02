package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.service.CategoryServiceI;

import com.bikkadit.electronic.store.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @MockBean
    private CategoryServiceImpl categoryServiceI;


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockmvc;

    Category category;

    @BeforeEach
    void init (){
        category = Category.builder()
                .title("sd card related category")
                .description("SD card available for every smart")
                .coverImage("abc.png").build();

    }
    @Test
    void createCategory() throws Exception {

        CategoryDto categoryDto= this.mapper.map(category, CategoryDto.class);
        //Stubbing
        Mockito.when(categoryServiceI.createCategory(Mockito.any())).thenReturn(categoryDto);

        //request create for url
        this.mockmvc.perform(
                MockMvcRequestBuilders.post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    private String convertObjectToJsonString(Object category) {

        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
          e.printStackTrace();
            return null;
        }

    }


    @Test
    void updateCategory() throws Exception {

        String categoryId ="xz";
        CategoryDto categoryDto = CategoryDto.builder()
                .title("sd card related category")
                .description("SD card available for every smart")
                .coverImage("abc.png").build();

        Mockito.when(categoryServiceI.updateCategory(Mockito.any(),Mockito.anyString())).thenReturn(categoryDto);

        this.mockmvc.perform(
                MockMvcRequestBuilders.put("/api/category/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void deleteCategory() {
    }

    @Test
    void getCategoryById() throws Exception {
        String categoryId ="sag";
        CategoryDto categoryDto = this.mapper.map(category, CategoryDto.class);

        Mockito.when(categoryServiceI.getCategoryById(Mockito.anyString())).thenReturn(categoryDto);

        this.mockmvc.perform(
                MockMvcRequestBuilders.get("/api/category/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.description").exists());
    }

    @Test
    void getAllCategories() throws Exception {
        CategoryDto categoryDto1 = CategoryDto.builder()
                .title("sd card related category1")
                .description("SD card not available for every smart")
                .coverImage("abc.png").build();
        CategoryDto categoryDto2 = CategoryDto.builder()
                .title("sd card related category2")
                .description("SD card available for every smart")
                .coverImage("axc.png").build();
        CategoryDto categoryDto3 = CategoryDto.builder()
                .title("sd card related category3")
                .description("SD card available for every smart")
                .coverImage("acd.png").build();

        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(categoryDto1, categoryDto2, categoryDto3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(categoryServiceI.getAllCategories(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockmvc.perform(
                        MockMvcRequestBuilders.get("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void searchCategoryByTitle() throws Exception {

        String keyword ="sd";

        CategoryDto categoryDto1 = CategoryDto.builder()
                .title("sd card related category1")
                .description("SD card not available for every smart")
                .coverImage("abc.png").build();
        CategoryDto categoryDto2 = CategoryDto.builder()
                .title("sd card related category2")
                .description("SD card available for every smart")
                .coverImage("axc.png").build();


        Mockito.when(categoryServiceI.searchCategoryByTitle(Mockito.anyString())).thenReturn(Arrays.asList(categoryDto1,categoryDto2));

        //request for url
        this.mockmvc.perform(
                        MockMvcRequestBuilders.get("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void uploadCoverImage() {
    }

    @Test
    void serverImage() {
    }
}