package com.nawhew.awsboot.web;

import com.nawhew.awsboot.config.auth.LoginUser;
import com.nawhew.awsboot.config.auth.dto.SessionUser;
import com.nawhew.awsboot.posts.application.PostsService;
import com.nawhew.awsboot.posts.dto.PostsResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", this.postsService.findAllOrderByIdDesc());
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
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
