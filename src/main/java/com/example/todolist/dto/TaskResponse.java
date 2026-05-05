package com.example.todolist.dto;

import java.time.LocalDateTime;

public record TaskResponse(
    Long id,
    String title,
    String description,
    boolean completed,
    LocalDateTime createdAt
) {}
