package com.nawhew.awsboot.posts.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    /**
     * Posts를 내림차순 정렬하여 모두 조회합니다.
     * @return 정렬된 Posts 목록
     */
    List<Posts> findAllByOrderByIdDesc();
}
