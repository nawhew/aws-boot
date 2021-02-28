package com.nawhew.awsboot.posts.dto;

import com.nawhew.awsboot.posts.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class PostsResponse {
    private Long id;
    private String title;
    private String content;
    private String author;

    public static PostsResponse to(Posts posts) {
        return new PostsResponse(posts.getId()
                , posts.getTitle(), posts.getContent(), posts.getAuthor());
    }
}
