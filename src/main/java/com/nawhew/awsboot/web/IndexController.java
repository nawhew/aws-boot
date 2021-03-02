package com.nawhew.awsboot.web;

import com.nawhew.awsboot.posts.application.PostsService;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", this.postsService.findAllOrderByIdDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable("id") Long postsId, Model model) {
        PostsResponse postsResponse = this.postsService.findById(postsId);
        model.addAttribute("post", postsResponse);
        return "posts-update";
    }
}
