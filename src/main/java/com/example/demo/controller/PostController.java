package com.example.demo.controller;

import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.dto.UpdateRequest;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(value = "api/test/post")
    public ResponseEntity<String> create(@RequestBody PostRequest postRequest){
        postService.create(postRequest);
        return new ResponseEntity<>("생성 성공", HttpStatus.OK);
    }

    @GetMapping(value = "api/test/post/{id}")
    public ResponseEntity<PostResponse> read (@PathVariable("id") String id){
        PostResponse response = postService.read(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "api/test/post/{id}")
    public ResponseEntity<PostResponse> update(@RequestBody UpdateRequest updateRequest, @PathVariable("id") String id){
        PostResponse response = postService.update(id,updateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "api/test/post/{id}")
    public ResponseEntity<String> create(@PathVariable("id") String id){
        postService.delete(id);
        return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
    }
}