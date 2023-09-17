package com.project.smartICT.requests;

import lombok.Data;

@Data
public class PostCreateRequest {
    Long id;
    String title;
    String content;
    Long userId;
}
