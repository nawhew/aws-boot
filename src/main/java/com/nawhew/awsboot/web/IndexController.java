package com.nawhew.awsboot.web;

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
    public String index(Model model) {
        model.addAttribute("posts", this.postsService.findAllOrderByIdDesc());
        this.addUserToModel(model);
        return "index";
    }

    /**
     * 인증 된 유저 정보가 있는 경우 Model 속성에 추가합니다.
     * @param model
     */
    private void addUserToModel(Model model) {
        SessionUser user = (SessionUser) this.httpSession.getAttribute("user");

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
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
