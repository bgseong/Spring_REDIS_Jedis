package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.dto.UpdateRequest;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void create(PostRequest request) {
        Post post = request.toEntity();
        postRepository.save(post);

    }

    public PostResponse read(Long id) {
        Post post = postRepository.findById(id).get();
        PostResponse response = new PostResponse(post);
        return response;
    }

    @Transactional
    public PostResponse update(Long id, UpdateRequest updateRequest) {
        Post post = postRepository.findById(id).get();
        post.setTitle(updateRequest.getTitle());
        post.setBody(updateRequest.getBody());
        PostResponse response = new PostResponse(post);
        return response;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}