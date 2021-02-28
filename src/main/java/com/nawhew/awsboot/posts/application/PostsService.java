package com.nawhew.awsboot.posts.application;

import com.nawhew.awsboot.posts.domain.Posts;
import com.nawhew.awsboot.posts.domain.PostsRepository;
import com.nawhew.awsboot.posts.dto.PostsRequest;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    public PostsResponse save(PostsRequest postsSaveRequestDto) {
        return PostsResponse.to(this.postsRepository.save(postsSaveRequestDto.toEntity()));
    }

    public PostsResponse findById(Long postsId) {
        return PostsResponse.to(this.postsRepository.findById(postsId)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID의 Posts가 없습니다.")));
    }

    @Transactional
    public PostsResponse update(Long postsId, PostsRequest postsRequest) {
        Posts persistPosts = this.postsRepository.findById(postsId).
                orElseThrow(() -> new IllegalArgumentException("요청한 ID의 Posts가 없습니다."));

        persistPosts.update(postsRequest.getTitle(), postsRequest.getContent());
        return PostsResponse.to(persistPosts);
    }
}
