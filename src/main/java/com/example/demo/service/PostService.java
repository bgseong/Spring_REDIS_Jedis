package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.dto.UpdateRequest;
import com.example.demo.repository.PostRepository;
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

    private final PostRepository postRepository;

    @SneakyThrows
    public void create(PostRequest request) {
        Post post = request.toEntity();
        String key = UUID.randomUUID().toString();
        post.setId(key);
        postRepository.save(post);

    }

    @SneakyThrows
    public PostResponse read(String id) {
        PostResponse response;
        if(redisTemplate.hasKey(id)){
            String jsonString = redisTemplate.opsForValue().get(id);
            response = objectMapper.readValue(jsonString,PostResponse.class);
        }
        else {
            Post post = postRepository.findById(id).get();
            redisTemplate.opsForValue().set(id,objectMapper.writeValueAsString(post));
            redisTemplate.expire(id,180,TimeUnit.SECONDS);
            response = new PostResponse(post);
        }
        return response;
    }

    @SneakyThrows
    @Transactional
    public PostResponse update(String id, UpdateRequest updateRequest) {
        Post post = postRepository.findById(id).get();
        post.setTitle(updateRequest.getTitle());
        post.setBody(updateRequest.getBody());
        PostResponse response = new PostResponse(post);
        return response;
    }

    @SneakyThrows
    public void delete(String id) {
        postRepository.deleteById(id);
    }
}