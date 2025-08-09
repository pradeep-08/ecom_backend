package com.webbious.ecommerce.utils;

import lombok.Data;

import java.util.List;

@Data
public class Report<F, T> {
    private List<T> content;
    private F filterBy;
}

