package com.nawhew.awsboot.posts.web;

import com.nawhew.awsboot.posts.application.PostsService;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import com.nawhew.awsboot.posts.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostsResponse> save(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        PostsResponse postsResponse = this.postsService.save(postsSaveRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/posts" + postsResponse.getId()))
                .body(postsResponse);
    }
}
