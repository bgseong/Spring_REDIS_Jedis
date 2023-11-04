package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.dto.UpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PostService {
    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void create(PostRequest request) {
        Post post = request.toEntity();
        String key = UUID.randomUUID().toString();
        post.setId(key);
        post.setCreate_time(System.currentTimeMillis());

        String postJson = objectMapper.writeValueAsString(post);

        redisTemplate.opsForValue().set(key,postJson);
        redisTemplate.expire(key,3600, TimeUnit.SECONDS);

    }

    @SneakyThrows
    public PostResponse read(String id) {
        String postJson = redisTemplate.opsForValue().get(id);
        PostResponse response = objectMapper.readValue(postJson, PostResponse.class);
        return response;
    }

    @SneakyThrows
    @Transactional
    public PostResponse update(String id, UpdateRequest updateRequest) {
        String postJson = redisTemplate.opsForValue().get(id);

        Post post = objectMapper.readValue(postJson, Post.class);
        post.setTitle(updateRequest.getTitle());
        post.setBody(updateRequest.getBody());

        postJson = objectMapper.writeValueAsString(post);
        redisTemplate.opsForValue().set(post.getId(),postJson);

        redisTemplate.expire(post.getId(),3600, TimeUnit.SECONDS);

        PostResponse response = new PostResponse(post);
        return response;
    }

    @SneakyThrows
    public void delete(String id) {
        redisTemplate.delete(id);
    }
}