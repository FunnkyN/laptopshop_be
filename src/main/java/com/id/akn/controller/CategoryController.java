package com.id.akn.controller;

import com.id.akn.exception.CategoryException;
import com.id.akn.request.CategoryDTO;
import com.id.akn.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/categories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private CategoryService categoryService;


    

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Byte id) throws CategoryException {
        return ResponseEntity.ok(categoryService.getCategoryDTOById(id));
    }

    
}
