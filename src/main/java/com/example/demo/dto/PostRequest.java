package com.example.demo.dto;

import com.example.demo.domain.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    public Post toEntity(){
        return Post.builder()
                .title(this.title)
                .body(this.body)
                .build();
    }

}