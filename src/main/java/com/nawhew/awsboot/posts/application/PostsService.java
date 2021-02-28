package com.nawhew.awsboot.posts.application;

import com.nawhew.awsboot.posts.domain.Posts;
import com.nawhew.awsboot.posts.domain.PostsRepository;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import com.nawhew.awsboot.posts.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    public PostsResponse save(PostsSaveRequestDto postsSaveRequestDto) {
        return PostsResponse.to(this.postsRepository.save(postsSaveRequestDto.toEntity()));
    }
}
