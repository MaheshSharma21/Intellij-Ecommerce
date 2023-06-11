package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.service.CategoryServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryServiceI categoryServiceI;


}
