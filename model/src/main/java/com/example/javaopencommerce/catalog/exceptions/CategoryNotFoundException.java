package com.example.javaopencommerce.catalog.exceptions;

import com.example.javaopencommerce.catalog.CategoryId;

import static java.lang.String.format;

public class CategoryNotFoundException extends RuntimeException {

    private static final String ERROR_MSG = "Category with id: %s does not exists!";

    public CategoryNotFoundException(CategoryId categoryId) {
        super(format(ERROR_MSG, categoryId.id()));
    }
}
