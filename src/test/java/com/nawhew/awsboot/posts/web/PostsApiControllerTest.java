package com.nawhew.awsboot.posts.web;

import com.nawhew.awsboot.posts.domain.PostsRepository;
import com.nawhew.awsboot.posts.dto.PostsRequest;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import org.junit.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        this.postsRepository.deleteAll();
    }

    @Test
    @DisplayName("Posts를 등록합니다.")
    public void save() {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용입니다.";

        // when
        ResponseEntity<PostsResponse> responseEntity = 포스트_등록_됨(title, content);
        PostsResponse postsResponse = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().containsKey("Location")).isTrue();
        assertThat(postsResponse.getId()).isNotNull();
        assertThat(postsResponse.getTitle()).isEqualTo(title);
        assertThat(postsResponse.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("Posts를 조회합니다.")
    public void findById() {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용입니다.";
        Long postsId = 포스트_등록_됨(title, content).getBody().getId();

        // when
        String url = "http://localhost:" + this.port + "/api/v1/posts/" + postsId;
        ResponseEntity<PostsResponse> foundPosts = this.restTemplate.getForEntity(URI.create(url), PostsResponse.class);
        PostsResponse postsResponse = foundPosts.getBody();

        // then
        assertThat(foundPosts.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postsResponse.getId()).isEqualTo(postsId);
        assertThat(postsResponse.getTitle()).isEqualTo(title);
        assertThat(postsResponse.getContent()).isEqualTo(content);
    }


    @Test
    @DisplayName("Posts를 수정합니다.")
    public void update() {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용입니다.";
        Long postsId = 포스트_등록_됨(title, content).getBody().getId();

        String updatedTitle = "테스트 제목 2";
        String updatedContent = "테스트 내용2입니다.";
        HttpEntity<PostsRequest> updatePostsRequest
                = new HttpEntity<>(PostsRequest.builder()
                                    .title(updatedTitle)
                                    .content(updatedContent)
                                    .build());
        String url = "http://localhost:" + this.port + "/api/v1/posts/" + postsId;

        // when
        ResponseEntity<PostsResponse> responseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, updatePostsRequest, PostsResponse.class);
        PostsResponse postsResponse = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postsResponse.getTitle()).isEqualTo(updatedTitle);
        assertThat(postsResponse.getContent()).isEqualTo(updatedContent);
    }

    private ResponseEntity<PostsResponse> 포스트_등록_됨(String title, String content) {
        PostsRequest requestDto = PostsRequest.builder()
                .title(title)
                .content(content)
                .author("nawhew")
                .build();
        String url = "http://localhost:" + this.port + "/api/v1/posts";

        return this.restTemplate.postForEntity(url, requestDto, PostsResponse.class);
    }


}