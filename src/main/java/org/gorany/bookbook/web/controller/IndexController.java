package org.gorany.bookbook.web.controller;

import lombok.RequiredArgsConstructor;
import org.gorany.bookbook.api.service.PostsService;
import org.gorany.bookbook.config.auth.LoginUser;
import org.gorany.bookbook.config.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    //private final HttpSession session;

    @GetMapping({"/", "/index"})
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("list", postsService.findAllDesc());

        Optional.ofNullable(user)
            .ifPresent(u -> model.addAttribute("userName", u.getName()));

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postsService.findById(id));
        return "posts-update";
    }
}
