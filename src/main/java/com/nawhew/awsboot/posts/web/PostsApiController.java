package com.nawhew.awsboot.posts.web;

import com.nawhew.awsboot.posts.application.PostsService;
import com.nawhew.awsboot.posts.dto.PostsRequest;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostsResponse> save(@RequestBody PostsRequest postsRequest) {
        PostsResponse postsResponse = this.postsService.save(postsRequest);
        return ResponseEntity.created(URI.create("/api/v1/posts" + postsResponse.getId()))
                .body(postsResponse);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostsResponse> findById(@PathVariable("id") Long postsId) {
        PostsResponse postsResponse = this.postsService.findById(postsId);
        return ResponseEntity.ok(postsResponse);
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<PostsResponse>> findAllOrderByIdDesc() {
        List<PostsResponse> postsResponses = this.postsService.findAllOrderByIdDesc();
        return ResponseEntity.ok(postsResponses);
    }

    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostsResponse> update(@PathVariable("id") Long postsId, @RequestBody PostsRequest postsRequest) {
        PostsResponse postsResponse = this.postsService.update(postsId, postsRequest);
        return ResponseEntity.ok(postsResponse);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity delete(@PathVariable("id") Long postsId) {
        this.postsService.delete(postsId);
        return ResponseEntity.noContent().build();
    }
}
