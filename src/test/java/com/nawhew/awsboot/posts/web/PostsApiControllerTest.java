package com.nawhew.awsboot.posts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nawhew.awsboot.posts.domain.PostsRepository;
import com.nawhew.awsboot.posts.dto.PostsRequest;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                                        .apply(springSecurity())
                                        .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Posts를 등록합니다.")
    public void save() throws Exception {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용입니다.";
        LocalDateTime now = LocalDateTime.now();

        // when
        PostsResponse postsResponse = 포스트_등록(title, content);

        // then
        assertThat(postsResponse.getId()).isNotNull();
        assertThat(postsResponse.getTitle()).isEqualTo(title);
        assertThat(postsResponse.getContent()).isEqualTo(content);
        assertThat(postsResponse.getCreatedDate()).isAfter(now);
    }


    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Posts를 조회합니다.")
    public void findById() throws Exception {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용입니다.";
        Long postsId = 포스트_등록(title, content).getId();

        // when
        PostsResponse postsResponse = 포스트_조회(postsId);

        // then
        assertThat(postsResponse.getId()).isEqualTo(postsId);
        assertThat(postsResponse.getTitle()).isEqualTo(title);
        assertThat(postsResponse.getContent()).isEqualTo(content);
    }


    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Posts를 수정합니다.")
    public void update() throws Exception {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용입니다.";
        Long postsId = 포스트_등록(title, content).getId();

        String updatedTitle = "테스트 제목 2";
        String updatedContent = "테스트 내용2입니다.";
        PostsResponse postsResponse = 포스트_수정(postsId, updatedTitle, updatedContent);

        // then
        assertThat(postsResponse.getTitle()).isEqualTo(updatedTitle);
        assertThat(postsResponse.getContent()).isEqualTo(updatedContent);
    }


    private PostsResponse 포스트_등록(String title, String content) throws Exception {
        PostsRequest requestDto = PostsRequest.builder()
                .title(title)
                .content(content)
                .author("nawhew")
                .build();
        String url = "http://localhost:" + this.port + "/api/v1/posts";

        // when
        MvcResult mvcResult = this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        return this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PostsResponse.class);
    }

    private PostsResponse 포스트_조회(Long postsId) throws Exception {
        String url = "http://localhost:" + this.port + "/api/v1/posts/" + postsId;

        // when
        MvcResult mvcResult = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        return this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PostsResponse.class);
    }

    private PostsResponse 포스트_수정(Long postsId, String title, String content) throws Exception {
        PostsRequest requestDto = PostsRequest.builder()
                .title(title)
                .content(content)
                .author("nawhew")
                .build();
        String url = "http://localhost:" + this.port + "/api/v1/posts/" + postsId;

        // when
        MvcResult mvcResult = this.mockMvc.perform(put(url)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        return this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PostsResponse.class);
    }

}