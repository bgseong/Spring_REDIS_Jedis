package com.example.demo.dto;

import com.example.demo.domain.Post;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String id;

    private String title;

    private String body;

    private Long create_time;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.create_time = post.getCreate_time();
    }
}