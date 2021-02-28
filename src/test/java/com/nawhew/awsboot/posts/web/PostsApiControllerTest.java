package com.nawhew.awsboot.posts.web;

import com.nawhew.awsboot.posts.domain.PostsRepository;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import com.nawhew.awsboot.posts.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    public void save() {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용입니다.";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("nawhew")
                .build();
        String url = "http://localhost:" + this.port + "/api/v1/posts";

        // when
        ResponseEntity<PostsResponse> responseEntity
                = this.restTemplate.postForEntity(url, requestDto, PostsResponse.class);
        PostsResponse postsResponse = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().containsKey("Location")).isTrue();
        assertThat(postsResponse.getId()).isNotNull();
        assertThat(postsResponse.getTitle()).isEqualTo(title);
        assertThat(postsResponse.getContent()).isEqualTo(content);
    }
}