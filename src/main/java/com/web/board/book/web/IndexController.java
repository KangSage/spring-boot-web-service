package com.web.board.book.web;

import com.web.board.book.config.auth.LoginUser;
import com.web.board.book.config.auth.dto.SessionUser;
import com.web.board.book.service.PostsService;
import com.web.board.book.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;

  @GetMapping("/")
  public String index(Model model, @LoginUser SessionUser user) {
    model.addAttribute("posts", postsService.findAllDesc());
    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    return "index";
  }

  @GetMapping("/posts/save")
  public String postSave() {
    return "posts-save";
  }

  @GetMapping("/posts/update/{id}")
  public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
    PostsResponseDto dto = postsService.findById(id);
    model.addAttribute("post", dto);
    if (dto.getUserId().equals(user.getId())) {
      model.addAttribute("userId", user.getId());
    }
    return "posts-update";
  }

}
