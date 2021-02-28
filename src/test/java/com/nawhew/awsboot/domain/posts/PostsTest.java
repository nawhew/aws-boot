package com.nawhew.awsboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        this.postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        Posts persistPosts = this.postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        // when
        Posts findPosts = this.postsRepository.findById(persistPosts.getId()).get();

        // then
        assertThat(findPosts.getId()).isNotNull();
        assertThat(findPosts.getTitle()).isEqualTo(title);
        assertThat(findPosts.getContent()).isEqualTo(content);
    }
}